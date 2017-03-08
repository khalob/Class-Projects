#include <stdio.h>      /* for printf() and fprintf() */
#include <sys/socket.h> /* for socket(), bind(), and connect() */
#include <arpa/inet.h>  /* for sockaddr_in and inet_ntoa() */
#include <stdlib.h>     /* for atoi() and exit() */
#include <string.h>     /* for memset() */
#include <unistd.h>     /* for close() */
#include <string.h>

#define MAXPENDING 5    /* Maximum outstanding connection requests */

void DieWithError(char *errorMessage);  /* Error handling function */
char* getClientMsg(int clntSocket);   /* TCP client handling function */
void searchNews(int clntSocket, char* keyword, FILE *fp);
void allNews(int clntSocket, FILE *fp);
char* appendStr(char* str1, char* str2);
void sendMessageToClient(int clntSocket, char* msg);

int main(int argc, char *argv[]){
	printf("Server started...\n");
	
    int servSock;                    /* Socket descriptor for server */
    int clntSock;                    /* Socket descriptor for client */
    struct sockaddr_in echoServAddr; /* Local address */
    struct sockaddr_in echoClntAddr; /* Client address */
    unsigned short echoServPort;     /* Server port */
    unsigned int clntLen;            /* Length of client address data structure */
	char userInput[30]; 			 //File name input
	char keywordInput[40];
	char fileName[40];
	FILE *fp;
    
    if (argc != 2){     /* Test for correct number of arguments */
        fprintf(stderr, "Usage:  %s <Server Port>\n", argv[0]);
        exit(1);
    }
    
    echoServPort = atoi(argv[1]);  /* First arg:  local port */
    
    /* Create socket for incoming connections */
    if ((servSock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
        DieWithError("socket() failed");
    
    /* Construct local address structure */
    memset(&echoServAddr, 0, sizeof(echoServAddr));   /* Zero out structure */
    echoServAddr.sin_family = AF_INET;                /* Internet address family */
    echoServAddr.sin_addr.s_addr = htonl(INADDR_ANY); /* Any incoming interface */
    echoServAddr.sin_port = htons(echoServPort);      /* Local port */
    
    /* Bind to the local address */
    if (bind(servSock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
        DieWithError("bind() failed");
	
	/* Mark the socket so it will listen for incoming connections */
	if (listen(servSock, MAXPENDING) < 0)
		DieWithError("listen() failed");

	/* Set the size of the in-out parameter */
	clntLen = sizeof(echoClntAddr);
	
	for(;;){ //loop until you open a file
		printf("Please type the news day you would like to read from. (ex: 11-21-2016)\n");
		fgets(userInput, 30, stdin); //get text from user
		userInput[strcspn(userInput, "\n")] = 0; //removes the trailing "\n" from userInput
		
		if(userInput[0] == 0){ //if the user did not type anything
			printf("You must enter a date. (ex: 11-21-2016)\n");
			continue;
		}else if(strchr(userInput, '.') == NULL){ //if the user did not include an extension
			snprintf(fileName, sizeof (fileName), "News Feed/%s.txt", userInput); //concatenates userInput + ".txt" into fileName
		}else{
			sprintf(fileName, userInput);
		}
		fp = fopen(fileName, "r");
		
		if(!fp){
			printf("%s does not exist.\n", fileName);
		}else{
			break;
		}
		printf("\n");
	}
	
	printf("File %s has been opened.\n", fileName);
    
    for (;;){ /* Run forever */
        /* Wait for a client to connect */
        if ((clntSock = accept(servSock, (struct sockaddr *) &echoClntAddr, &clntLen)) < 0)
            DieWithError("accept() failed");
        
        /* clntSock is connected to a client! */
       
        printf("Handling client %s\n", inet_ntoa(echoClntAddr.sin_addr));
        char* command = getClientMsg(clntSock);
		printf("Command is %s\n", command);
        if(strcmp(command, "All") == 0 || strcmp(command, "all") == 0){
			allNews(clntSock, fp);
		}else if(strcmp(command, "Keyword") == 0 || strcmp(command, "keyword") == 0){
			char* key = getClientMsg(clntSock);
			searchNews(clntSock, key, fp);
		}
		
		close(clntSock);    /* Close client socket */
    }
    /* NOT REACHED */
}


void searchNews(int clntSocket, char* keyword, FILE *fp){
	char article[5000]; //string to hold current article
	char buff[1000]; //string to hold current line
	int keywordFound = 0; //boolean to indicate keyword has been found in this article
	int continueRunning = 1; //boolean to tell the while loop when to stop
	
	keyword[strcspn(keyword, "\n")] = 0; //removes the trailing "\n" from userInput
	printf("Keyword:%s\n", keyword);
	
	long size; //size of total file
	char *totalMessage; //while hold all articles with keyword
	fseek( fp , 0L , SEEK_END); //put pointer at start of file
	size = ftell(fp); //get size of file
	rewind(fp); //go back to the beginning
	
	char fileL[30]; //turn file size into string 
	sprintf(fileL, "%d", (size+1));
	sendMessageToClient(clntSocket, fileL); //send size to client
	
	/* allocate memory of file size */
	totalMessage = calloc( 1, size+200 );  
	
	while(continueRunning == 1){
		if(fgets(buff, 1000, fp) != NULL){ //if there is more lines
			continueRunning = 1;
		}else{
			continueRunning = 0;
		}
		
		if(strstr(buff, "#item") != NULL || continueRunning == 0){ //if we found a new article or this is the last article in the file
			if(keywordFound == 1){ 
				strcat(totalMessage, article); //add article to our total message
			}
			keywordFound = 0;
			sprintf(article, "%s", ""); //clear article
			sprintf(article, "\n\t\t--ARTICLE FOUND--\n>>%s", appendStr(article, buff)); //add formatting for the next article
		}else{
			/* Formatting for author and source */
			if(((buff[0] == 'b' || buff[0] == 'B' ) && buff[1] == 'y')){ 
				sprintf(article, "\t%s", appendStr(article, buff));
			}else if((buff[0] == 'S' && buff[1] == 'o' && buff[2] == 'u' && buff[3] == 'r' && buff[4] == 'c' && buff[5] == 'e')){
				sprintf(article, "\t%s", appendStr(article, buff));
			}else{
				sprintf(article, "%s", appendStr(article, buff));
			}
		}
		
		if(strstr(buff, keyword) != NULL){ //if current line contains keyword
			keywordFound = 1;
		}
	}
	printf("Sending articles with keyword \"%s\" to the client...\n", keyword);
	sendMessageToClient(clntSocket, totalMessage); //send all of the articles found to client
	free(totalMessage); //free up this variable from memory
}

void allNews(int clntSocket, FILE *fp){
	char lineBuff[900];
	char tempBuff[950];

	long size;
	char *buff;
	fseek( fp , 0L , SEEK_END);
	size = ftell(fp);
	rewind(fp);
	
	char fileL[30];
	sprintf(fileL, "%d", (size+1));
	sendMessageToClient(clntSocket, fileL);
	
	/* allocate memory of file size */
	buff = calloc( 1, size+200 );  

	while(fgets(lineBuff, 900, fp) != NULL){
		if(strstr(lineBuff, "#item") != NULL){
			memmove(lineBuff, lineBuff+6, strlen(lineBuff));
			sprintf(tempBuff, ">>%s", lineBuff);
		}else if(((lineBuff[0] == 'b' || lineBuff[0] == 'B' ) && lineBuff[1] == 'y')){
			sprintf(tempBuff, "  %s",  lineBuff);
		}else if((lineBuff[0] == 'S' && lineBuff[1] == 'o' && lineBuff[2] == 'u' && lineBuff[3] == 'r' && lineBuff[4] == 'c' && lineBuff[5] == 'e')){
			sprintf(tempBuff, "  %s", lineBuff);
		}else{
			sprintf(tempBuff, "%s", lineBuff);
		}
		strcat(buff, tempBuff);
	}
	printf("Sending all articles to the client...\n");
	sendMessageToClient(clntSocket, buff);
	free(buff); 
}

char* appendStr(char* str1, char* str2){
	char * new_str;
	if((new_str = malloc(strlen(str1)+strlen(str2)+1)) != NULL){
		new_str[0] = '\0';   // makes empty string
		strcat(new_str, str1);
		strcat(new_str, str2);
	} else {
		printf("malloc failed!\n");
	}
	return new_str;
}