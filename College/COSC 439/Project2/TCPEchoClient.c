#include <stdio.h>      /* for printf() and fprintf() */
#include <sys/socket.h> /* for socket(), connect(), send(), and recv() */
#include <arpa/inet.h>  /* for sockaddr_in and inet_addr() */
#include <stdlib.h>     /* for atoi() and exit() */
#include <string.h>     /* for memset() */
#include <unistd.h>     /* for close() */

#define RCVBUFSIZE 100   /* Size of receive buffer */

void DieWithError(char *errorMessage);  /* Error handling function */

int main(int argc, char *argv[]){
    int sock;                        /* Socket descriptor */
    struct sockaddr_in echoServAddr; /* Echo server address */
    unsigned short echoServPort;     /* Echo server port */
    char *servIP;                    /* Server IP address (dotted quad) */
    char *echoString;                /* String to send to echo server */
    char echoBuffer[RCVBUFSIZE];     /* Buffer for echo string */
    unsigned int echoStringLen;      /* Length of string to echo */
    int bytesRcvd, totalBytesRcvd;   /* Bytes read in single recv() and total bytes read */
	char* textInFile;
	int sizeOfFile;
	char command[16];				 //holds string for the user's command type
	char keyword[20];				 //holds string for the user's keyword in a search
    
	if ((argc < 2) || (argc > 3)){    /* Test for correct number of arguments */
        fprintf(stderr,"Usage: %s <Server IP> [<Port>]\n", argv[0]);
        exit(1);
    }
	
    servIP = argv[1];           /* First arg: server IP address (dotted quad) */

    if (argc == 3)
        echoServPort = atoi(argv[2]);  /* Use given port, if any */
    else{
        echoServPort = 7;  /* 7 is the well-known port for the echo service */
	}
	
	/* Construct the server address structure */
	memset(&echoServAddr, 0, sizeof(echoServAddr));     /* Zero out structure */
	echoServAddr.sin_family      = AF_INET;             /* Internet address family */
	echoServAddr.sin_addr.s_addr = inet_addr(servIP);   /* Server IP address */
	echoServAddr.sin_port        = htons(echoServPort); /* Server port */	
    
	
	/* Loop forever */
	for(;;){ 
		printf("\n\nList of commands:\n>All (Requests all of todays news) \n>Keyword (Search today's news for a keyword up to 16 characters) \n>Quit (End the program)\nInput: "); //prompt user
		scanf("%s", command); //store text from user into command
		
		if(strcmp(command, "All") == 0 || strcmp(command, "all") == 0){
			printf("Requesting Today's newfeed...\n");

			/* Create a reliable, stream socket using TCP */
			if ((sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
				DieWithError("socket() failed");

			/* Establish the connection to the echo server */
			if (connect(sock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
				DieWithError("connect() failed");

			/* Send the string to the server */
			if (send(sock, command, sizeof(command), 0) <= 0)
				DieWithError("send() sent a different number of bytes than expected");

			recv(sock, echoBuffer, RCVBUFSIZE, 0); //get size of file
			sizeOfFile = atoi(echoBuffer); //covert to int
			
			textInFile = calloc( 1, sizeOfFile+200 );  //allocate memory 
			printf("File size %i\n", sizeOfFile);      /* Print the echo buffer */
			
			printf("Recieving all news articles of today from server...\n");
			printf("\t\t\t----Today's News----\n");
			
			recv(sock, textInFile, sizeOfFile, 0);
			printf("%s\n", textInFile);      		/* Print the textInFile buffer */
			
			/* Close connect to the server */
			close(sock);
			free(textInFile);
		}else if(strcmp(command, "Keyword") == 0 || strcmp(command, "keyword") == 0){
			printf("Enter in the keyword you would like to search today's news for.\nInput: ");
			scanf("%s", keyword); //store text from user into keyword'
			
			if(strcmp(keyword, "Quit") == 0 || strcmp(keyword, "quit") == 0){
				close(sock);
				exit(0);
			}
			
			/* Create a reliable, stream socket using TCP */
			if ((sock = socket(PF_INET, SOCK_STREAM, IPPROTO_TCP)) < 0)
				DieWithError("socket() failed");

			/* Establish the connection to the echo server */
			if (connect(sock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
				DieWithError("connect() failed");

			/* Send the command to the server */
			if (send(sock, command, sizeof(command), 0) <= 0)
				DieWithError("send() sent a different number of bytes than expected");
			
			/* Send the keyword to the server */
			if (send(sock, keyword, sizeof(keyword), 0) <= 0)
				DieWithError("send() sent a different number of bytes than expected");
			
			recv(sock, echoBuffer, RCVBUFSIZE, 0); //get size of file
			sizeOfFile = atoi(echoBuffer); //convert to int
			textInFile = calloc( 1, sizeOfFile+200 );  //allocate the memory 
			
			printf("Recieving articles with keyword \"%s\" from server...\n", keyword);
			printf("\n\n\t\t\t----\"%s\" Results----\n", keyword);
			recv(sock, textInFile, sizeOfFile+200, 0); 
			printf("%s\n", textInFile);     	/* Print the textInFile buffer */
			
			close(sock);
			free(textInFile);
		}else if(strcmp(command, "Quit") == 0 || strcmp(command, "quit") == 0){
			close(sock);
			exit(0);
		}else{
			printf("Sorry, %s is not a known command. Example: Keyword\n", command);
		}
	}
}
