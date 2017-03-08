#include <stdio.h>      /* for printf() and fprintf() */
#include <sys/socket.h> /* for socket(), connect(), sendto(), and recvfrom() */
#include <arpa/inet.h>  /* for sockaddr_in and inet_addr() */
#include <stdlib.h>     /* for atoi() and exit() */
#include <string.h>     /* for memset() */
#include <unistd.h>     /* for close() */
#define ECHOMAX 255     /* Longest string to echo */

void DieWithError(char *errorMessage);  /* External error handling function */

enum TYPES {Login, Post, Activate, Subscribe, Unsubscribe, Logout };

typedef struct {
	unsigned int LeaderID;                                    /* unique client identifier */
	char message[100];                                        /* text message */
} ServerMessage;   

typedef struct {
	enum TYPES request_Type;   							      /* same size as an unsigned int */
	unsigned int UserID;                                      /* unique client identifier */
	unsigned int LeaderID;                                    /* unique client identifier */
	char message[100];                                        /* text message*/
} ClientMessage;                                              /* an unsigned int is 32 bits = 4 bytes */

int main(int argc, char *argv[]){
	ServerMessage * tempMsg;         /* Holds the poster's id and message, sent from server */
	int numOfPosts = 0;              /* Used to know how many posts user must recieve from server */
	int isSuccess;        	         /* 1 for true, 0 for false*/
	int currentID = 0;               /* Current Client ID */
    int sock;                        /* Socket descriptor */
    struct sockaddr_in echoServAddr; /* Echo server address */
    struct sockaddr_in fromAddr;     /* Source address of echo */
    unsigned short echoServPort;     /* Echo server port */
    unsigned int fromSize;           /* In-out of address size for recvfrom() */
    char *servIP;                    /* IP address of server */
    char echoBuffer[ECHOMAX+1];      /* Buffer for receiving echoed string */
    int echoStringLen;               /* Length of string to echo */
    int respStringLen;               /* Length of received response */

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
	
    /* Create a datagram/UDP socket */
    if ((sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0)
        DieWithError("socket() failed");

    /* Construct the server address structure */
    memset(&echoServAddr, 0, sizeof(echoServAddr));    /* Zero out structure */
    echoServAddr.sin_family = AF_INET;                 /* Internet addr family */
    echoServAddr.sin_addr.s_addr = inet_addr(servIP);  /* Server IP address */
    echoServAddr.sin_port   = htons(echoServPort);     /* Server port */

	/* Loop forever */
	for(;;){ 
		char command[16]; //holds string for the user's command type
		printf("List of commands:\n>Login\n>Post\n>Activate\n>Subscribe\n>Unsubscribe\n>Logout\n>Quit\n\n"); //prompt user
		scanf("%s", command); //store text from user into command

		/* Based on command type, preform actions */
		if(strcmp(command, "Login") == 0){
			char givenUserID[20]; //holds userid given as text from user
			char firstc = getc(stdin); //clears first space from buffer
			if(firstc != '\n'){ //the user did not sent a command without a parameter
				fgets(givenUserID, 20, stdin); //get text from user
			}
			
			if(givenUserID[0] == '\n' || firstc == '\n'){ //is the command without a parameter?
				printf("Usage: Login <1-100>\nExample: Login 3\n");
			}else if(givenUserID[0] == '0' && givenUserID[1] == '\n'){//did the user try to use UserID zero?
				printf("UserID cannot be zero.\n");
			}else if(atoi(givenUserID) == 0){ //does the text given contains characters?
				printf("UserID cannot contain characters.\n");
			}else if(atoi(givenUserID) > 100 || atoi(givenUserID) < 1){ //is the number betwee 1 and 100?
				printf("UserID must be between 1 and 100.\n");
			}else if(currentID != 0){ //is the user already logged in?
				printf("You must logout before you can login another user.\n");
			}else{	
				printf("Your UserID is: %d\n", atoi(givenUserID));
				ClientMessage myMsg; //intialize a message so we can send it to the server
				myMsg.request_Type = Login; //set the request type
				myMsg.UserID = atoi(givenUserID); //set the id to what was typed in

				/* Send the login attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Login attempt sent!\n");
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							currentID = atoi(givenUserID); //set currentID to the user we logged in as 
							printf("Login attempt successful!\n");
						}else{
							printf("%s\n", echoBuffer);
						}
					}
				}
			}
		}else if(strcmp(command, "Post") == 0){
			char msg[100]; //holds message that will be typed
			char firstc = getc(stdin); //clears first space from buffer
			if(firstc != '\n'){ //the user did not sent a command without a parameter
				fgets(msg, 100, stdin); //get text from user
			}
			
			if(msg[0] == '\n' || firstc == '\n'){ //is the message blank?
				printf("Usage: Post <Your Message>\n");
			}else if(currentID == 0){ //is the user not logged in
				printf("You must be logged in before you can post a message.\n");
			}else{ 
				ClientMessage myMsg; //holds the information to send to server
				myMsg.request_Type = Post; //store the request type
				myMsg.UserID = currentID; //store the current user's id
				sprintf(myMsg.message, msg); //store the message that was typed in
				/* Send post attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Post attempt sent!\n");
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							printf("Post attempt successful!\n");
						}else{
							printf("%s\n", echoBuffer);
						}
					}
				}
			}
		}else if(strcmp(command, "Logout") == 0){
			if(currentID !=0){
				ClientMessage myMsg; //create message to send to server
				myMsg.request_Type = Logout; //set the request type
				myMsg.UserID = currentID; //set the id to current UserID
				/* Send the logout attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Logout attempt sent!\n");
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							printf("Logout attempt successful!\n");
							currentID = 0; //clear current UserID
						}else{
							printf("Unkown Error during logout.\n");
						}
					}
				}
			}else{
				printf("You must be logged in before you can logout.\n");
			}
		}else if(strcmp(command, "Activate") == 0){
			if(currentID == 0){ //if the user is not logged in
				printf("You must login before you can activate your feed.\n");
			}else{
				ClientMessage myMsg; //create message to send to server
				myMsg.request_Type = Activate; //set the request type
				myMsg.UserID = currentID; //set the id to current UserID
				/* Send the activate attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Activate attempt sent!\n");
					/* Recieve acknowledgement */
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							printf("Activate attempt successful!\n");
							tempMsg = malloc(sizeof(ServerMessage)); //will hold message and poster's id
							numOfPosts = 0; // the number of post we need to recieve
							//Recieve the number of posts we will need to read in
							if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
								DieWithError("recvfrom() failed");
							}else{
								numOfPosts = atoi(echoBuffer); //set number of posts to what the server gave us
								
								/* Loop through and print those posts */
								for(; numOfPosts>0; numOfPosts--){
									if ((respStringLen = recvfrom(sock, tempMsg, sizeof(*tempMsg), 0, (struct sockaddr *) &fromAddr, &fromSize)) < 0){
										DieWithError("recvfrom() failed when recieving posts during activation.");
									}else{
										//Print the post with LeaderID and message
										printf("\n\nUser: %d said, \n>>%s\n", tempMsg->LeaderID, tempMsg->message);
									}
								}
							}
						}else{
							printf("Activate attempt unsuccessful!\n%s\n", echoBuffer);
						}
					}
				}
			}
		}else if(strcmp(command, "Subscribe") == 0){
			char givenSubID[20]; //holds given subscription ID
			char firstc = getc(stdin); //clears first space from buffer
			if(firstc != '\n'){ //if there is a parameter
				fgets(givenSubID, 20, stdin); //store text from user
			}

			if(givenSubID[0] == '\n' || firstc == '\n'){ //is message blank?
				printf("Usage: Subscribe <LeaderID>\nExample: Subscribe 123\n");
			}else if(currentID == 0){ //is the user logged in?
				printf("You must be logged in before you can subscribe to another user.\n");
			}else if(currentID == atoi(givenSubID)){ //are they trying to subscribe to the current login?
				printf("You cannot subscribe to yourself.\n");
			}else if(givenSubID[0] == '0' && givenSubID[1] == '\n'){ //are they trying to subscribe to id of zero?
				printf("LeaderID cannot be zero.\n");
			}else if(atoi(givenSubID) > 100 || atoi(givenSubID) < 1){ //is the given id between 1 and 100?
				printf("LeaderID must be between 1 and 100.\n");
			}else if(atoi(givenSubID) == 0){ //does the given id contain characters?
				printf("LeaderID cannot contain characters.\n");
			}else{	
				ClientMessage myMsg; //create message to send to server
				myMsg.request_Type = Subscribe; //set the request type
				myMsg.UserID = currentID; //set the id to current UserID
				myMsg.LeaderID = atoi(givenSubID); //set the id to given leaderID
				/* Send the subscription attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Subscribe attempt sent from user %d to follow user %d\n", currentID, atoi(givenSubID));
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							printf("Subscribe attempt successful!\n");
						}else{
							printf("Subscribe attempt unsuccessful!\n%s\n", echoBuffer);
						}
					}
				}
				
			}
		}else if(strcmp(command, "Unsubscribe") == 0){
			char givenUnsubID[20]; //holds given unsubscription ID
			char firstc = getc(stdin); //clears first space from buffer
			if(firstc != '\n'){ //if there is a parameter
				fgets(givenUnsubID, 20, stdin); //store text from user
			}

			if(givenUnsubID[0] == '\n' || firstc == '\n'){ //is message blank?
				printf("Usage: Unsubscribe <LeaderID>\nExample: Unsubscribe 123\n");
			}else if(currentID == 0){ //is the user logged in?
				printf("You must be logged in before you can unsubscribe from a user.\n");
			}else if(givenUnsubID[0] == '0' && givenUnsubID[1] == '\n'){ // is the given id zero?
				printf("UserID cannot be zero.\n");
			}else if(atoi(givenUnsubID) > 100 || atoi(givenUnsubID) < 1){ //is the given id between 1 and 100?
				printf("LeaderID must be between 1 and 100.\n");
			}else if(atoi(givenUnsubID) == 0){ //does the given id contain characters?
				printf("UserID cannot contain characters.\n");
			}else{	
				ClientMessage myMsg; //create message to send to server
				myMsg.request_Type = Unsubscribe; //set the request type
				myMsg.UserID = currentID; //set the id to current UserID
				myMsg.LeaderID = atoi(givenUnsubID); //set the id to given leaderID
				/* Send the login attempt to the server */
				if (sendto(sock, (struct ClientMessage*)&myMsg, sizeof(myMsg), 0, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) != sizeof(myMsg)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Unsubscribe attempt sent!\n");
					if ((respStringLen = recvfrom(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &fromAddr, &fromSize)) < ECHOMAX){
						DieWithError("recvfrom() failed");
					}else{
						if(strcmp(echoBuffer, "y") == 0){
							printf("Unsubscribe attempt successful!\n");
						}else{
							printf("Unsubscribe attempt unsuccessful!\n%s\n", echoBuffer);
						}
					}
				}
			}
		}else if(strcmp(command, "Quit") == 0){
			close(sock);
			exit(0);
		}
	}
	/* NEVER REACHED (end of for loop) */
}
