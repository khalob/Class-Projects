#include <stdio.h>      /* for printf() and fprintf() */
#include <sys/socket.h> /* for socket() and bind() */
#include <arpa/inet.h>  /* for sockaddr_in and inet_ntoa() */
#include <stdlib.h>     /* for atoi() and exit() */
#include <string.h>     /* for memset() */
#include <unistd.h>     /* for close() */
#define ECHOMAX 255     /* Longest string to echo */

void DieWithError(char *errorMessage);  /* External error handling function */

enum TYPES {Login, Post, Activate, Subscribe, Unsubscribe, Logout }; 

typedef struct {
	enum TYPES request_Type;  								  /* same size as an unsigned int */
	unsigned int UserID;                                      /* unique client identifier */
	unsigned int LeaderID;                                    /* unique client identifier */
	char message[100];                                        /* text message*/
} ClientMessage; 

typedef struct {
	unsigned int LeaderID;                                    /* unique client identifier */
	char message[100];                                        /* text message */
} ServerMessage;                                              /* an unsigned int is 32 bits = 4 bytes */

typedef struct {
	char * address;                                 		  /* ip address */
	unsigned int port;                                    	  /* port number */
	int following[10];                                        /* who the client is following */
	unsigned int ID;                                          /* unique client identifier */
	ClientMessage posts[10];  							      /* messages pending to be recieved */
} Client; 
	
	
int main(int argc, char *argv[]){
	
	printf("Server started!\n");
	
	int indexCurConn = 0;            /* Index of current connection */
	int isEmpty = 0; 				 /* 1 for true, 0 for false */
	int alrContains = 0; 		     /* 1 for true, 0 for false */
	int i = 0; 						 /* Used in for loops */
	int x = 0; 						 /* Used in for loops */ 
	int p = 0; 						 /* Used in for loops */
    int sock;                        /* Socket */
	ClientMessage * tempMsg;         /* Holds information sent from client */
    struct sockaddr_in echoServAddr; /* Local address */
    struct sockaddr_in echoClntAddr; /* Client address */
    unsigned int cliAddrLen;         /* Length of incoming message */
    char echoBuffer[ECHOMAX];        /* Buffer for echo string */
    unsigned short echoServPort;     /* Server port */
    int recvMsgSize;                 /* Size of received message */
	Client curUsers[100];  		     /* Currently Connected Clients position should be your UserID-1 */
	
    if (argc != 2){         /* Test for correct number of parameters */
        fprintf(stderr,"Usage:  %s <UDP SERVER PORT>\n", argv[0]);
        exit(1);
    }

    echoServPort = atoi(argv[1]);  /* First arg:  local port */
	
    /* Create socket for sending/receiving datagrams */
    if ((sock = socket(PF_INET, SOCK_DGRAM, IPPROTO_UDP)) < 0)
        DieWithError("socket() failed");

    /* Construct local address structure */
    memset(&echoServAddr, 0, sizeof(echoServAddr));   /* Zero out structure */
    echoServAddr.sin_family = AF_INET;                /* Internet address family */
    echoServAddr.sin_addr.s_addr = htonl(INADDR_ANY); /* Any incoming interface */
    echoServAddr.sin_port = htons(echoServPort);      /* Local port */

    /* Bind to the local address */
    if (bind(sock, (struct sockaddr *) &echoServAddr, sizeof(echoServAddr)) < 0)
        DieWithError("bind() failed");
	
	/* Run forever */
    for (;;){ 
        /* Set the size of the in-out parameter */
        cliAddrLen = sizeof(echoClntAddr);
		
		/* Allocate memory for tempMsg */
		tempMsg = malloc(sizeof(ClientMessage));
		
		/* Attempt to recieve a message from a client */
		if ((recvMsgSize = recvfrom(sock, tempMsg, sizeof(*tempMsg), 0, (struct sockaddr *) &echoClntAddr, &cliAddrLen)) < 0)
            DieWithError("recvfrom() failed");
		
		/* Handle client message based on the request_Type */
		if(tempMsg->request_Type == Login){ 
		
			if(curUsers[tempMsg->UserID - 1].ID == 0){ //if the UserID isn't already logged in 
				curUsers[tempMsg->UserID - 1].address = inet_ntoa(echoClntAddr.sin_addr); //store ip address
				curUsers[tempMsg->UserID - 1].port = ntohs(echoClntAddr.sin_port); //store their port
				curUsers[tempMsg->UserID - 1].ID = tempMsg->UserID; //store their UserID
				indexCurConn = indexCurConn + 1; //iterate index of current user
				printf("Client %s on user %d has logged in.\n", inet_ntoa(echoClntAddr.sin_addr), tempMsg->UserID);
				printf("Current number of connections: %d\n", indexCurConn);	
				sprintf(echoBuffer, "y"); //set buffer for client to be confirmed that user logged in
			}else{
				sprintf(echoBuffer, "This UserID is already in use. Please try again later."); //set buffer with the error
			}
			
			/* Send confirmation of error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}
			
		}else if(tempMsg->request_Type == Logout){ 
			sprintf(echoBuffer, "y"); //set buffer to confirm logout was successful
			
			/* Send confirmation of error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}else{
				curUsers[(tempMsg->UserID) - 1].ID = 0;
				indexCurConn = indexCurConn - 1; //decrement index of current users
				printf("Client %s has logged out of user %d.\n", inet_ntoa(echoClntAddr.sin_addr), tempMsg->UserID);
				printf("Current number of connections: %d\n", indexCurConn);
			}
		}else if(tempMsg->request_Type == Post){ 		
			/* Store the size of curUser array */
			int user_size = *(&curUsers + 1) - curUsers;
			sprintf(echoBuffer, ""); //clear echoBuffer, because we will build a message in the for loop, if their are multiple users with full inboxes
			for(i=0;i<user_size; i++){ //for each user	
				if(curUsers[i].port != 0){ //if they have connected before
					int following_size = *(&curUsers[i].following + 1) - curUsers[i].following; 
					for(x=0;x<following_size; x++){ //search the list of users they are following
						if(curUsers[i].following[x] == tempMsg->UserID){ //if they are following this poster
							int posts_size = *(&curUsers[i].posts + 1) - curUsers[i].posts; 
							for(p=0;p<posts_size;p++){ //search through their current pending posts
								if(curUsers[i].posts[p].message[0] == '\0'){ //find the next free slot
									curUsers[i].posts[p].UserID = tempMsg->UserID; //store this poster's ID and 
									sprintf(curUsers[i].posts[p].message, tempMsg->message); //store the message they sent
									break; //we can stop looking for a blank slot in this user's pending posts
								}
							}
							if(p == posts_size){ //if we didnt break from the loop, then we didnt find an empty post
								sprintf(echoBuffer, "%s\nYour post was made, but user %d didn't get your message, because their inbox is full.", echoBuffer, i+1); //let poster know that a user didn't get their message, because their inbox is full
							}
						} 
					}
				}
			}
			
			sprintf(echoBuffer, "y"); //set buffer to confirm post was successful
			/* Send confirmation of error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}else{
				printf("Client %s with UserId of %d just posted a message.\n", inet_ntoa(echoClntAddr.sin_addr), tempMsg->UserID);
			}
		}else if(tempMsg->request_Type == Activate){ 	
			/* Store the size of curUser[indexOfCurUser].posts array */		
			int posts_size = *(&curUsers[tempMsg->UserID-1].posts + 1) - curUsers[tempMsg->UserID-1].posts; 
			/* Intialize an array of ServerMessage that we will use to store pending posts to send to the client. */	
			ServerMessage tempPosts[10];
			i=0; //Make sure i is zero before using it again
			
			for(p=0;p<posts_size;p++){ //search through the posts
				if(curUsers[tempMsg->UserID-1].posts[p].message[0] != '\0'){//if we found a non-blank message
					tempPosts[i].LeaderID = curUsers[tempMsg->UserID-1].posts[p].UserID; //store the LeaderID in our tempPosts
					sprintf(tempPosts[i].message, curUsers[tempMsg->UserID-1].posts[p].message); //store the message in our tempPosts
					sprintf(curUsers[tempMsg->UserID-1].posts[p].message, ""); //clear message, so that it is no longer pending for the user
					i++; //iterate i
				}
			}
			
			if(i==0){ //if zero posts were found
				sprintf(echoBuffer, "There are no new messages from the users your are following."); //set error message
			}else{
				sprintf(echoBuffer, "y"); //set buffer to confirm activation was successful
			}
			
			/* Send confirmation or error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}else{
				/* Let the client know the number of messages we are going to send them */
				sprintf(echoBuffer, "%d", i); //store number of messages into the buffer
				/* Send that number to the client */
				if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
					DieWithError("sendto() sent a different number of bytes than expected");
				}else{
					printf("Client %s with UserId of %d requested their feed of %d messages.\n", inet_ntoa(echoClntAddr.sin_addr), tempMsg->UserID, i);
					/* Loop through and send all pending posts to the client (latest posts are sent first) */
					for(; i>0; i--){
						if (sendto(sock, (struct ServerMessage*)&tempPosts[i-1], sizeof(tempPosts[i-1]), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) < 0){
							DieWithError("sendto() failed when sending posts during activation.");
						}else{
							printf("Post %d sent.\n", i);
						}
					}
				}
			}
			
		}else if(tempMsg->request_Type == Subscribe){ 		
			int tempArr[10] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //create emtpy array
			alrContains = 0; //make sure our boolean statement is default false
			/* store the size of our blank array we made */
			int array_size = *(&tempArr + 1) - tempArr;
			
			/* Copy current user's following array into tempArr */
			for(i=0;i<array_size; i++){
				tempArr[i] = curUsers[tempMsg->UserID-1].following[i];
			}
			
			for(i=0; i<array_size; i++){//search through user's subscriptions
				if(tempArr[i] == 0){ //if slots is empty
					tempArr[i] = tempMsg->LeaderID; //add a new user at this spot in the list of subscriptions
					break; //spot was found, so no need to continue searching
				}else if(tempArr[i] == tempMsg->LeaderID){ //if the user is already following this given id
					alrContains = 1; //make sure we set our boolean to true
					break; //leave loop, because we have an error
				}
			}
			
			if(alrContains == 1){ //if the list already contains the given LeaderID
				sprintf(echoBuffer, "You are already subscribed to user %d.", tempMsg->LeaderID);
			}else if(i == array_size){ //if we went through the entire list without finding a zero (the loop would break before last iteration and i would equal 9 if a blank spot was not found)
				sprintf(echoBuffer, "Your subscription list is full. Please unsubscribe from a user first.");
			}else{ //if we found a blank spot
				sprintf(echoBuffer, "y"); //set buffer to confirm success
				
				/* Copy the modified tempArr into the user's following array */
				for(i=0; i<array_size; i++){
					curUsers[tempMsg->UserID-1].following[i] = tempArr[i];
				}
				printf("User %d is now following user %d\n", tempMsg->UserID, tempMsg->LeaderID);
			}
			
			/* Send confirmation of error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}
		}else if(tempMsg->request_Type == Unsubscribe){ 		
			int tempArr[10] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};  //create emtpy array
			isEmpty = 1; //make sure our boolean statement is default true
			/* store the size of our blank array we made */
			int array_size = *(&tempArr + 1) - tempArr;
			
			/* Copy current user's following array into tempArr */
			for(i=0;i<array_size; i++){
				tempArr[i] = curUsers[tempMsg->UserID-1].following[i];
			}
			
			for(i=0; i<array_size; i++){ //search tempArr
				if(tempArr[i] == tempMsg->LeaderID){ //if we find the given ID in the user's subscriptions
					tempArr[i] = 0; //remove it from the list
					isEmpty = 0; //set our boolean to false, because the list had at least one item
					break; //leave the loop, we found what we were looking for
				}
				if(tempArr[i] != 0){ //if the array is not empty
					isEmpty = 0; //set our boolean to false
				}
			}
			if(isEmpty == 1){ //if the list is empty
				sprintf(echoBuffer, "Your subscription list is empty. Please subscribe before attempting to remove a subscription.");
			}else if(i == array_size){ //if we have gone through whole list without finding the given LeaderID
				sprintf(echoBuffer, "Could not find user %d within your subscription list.", tempMsg->LeaderID);
			}else{ //if we found the id we were looking for
				sprintf(echoBuffer, "y"); //set buffer to confirm success
				
				/* Copy the modified tempArry into the user's following array */
				for(i=0; i<array_size; i++){
					curUsers[tempMsg->UserID-1].following[i] = tempArr[i];
				}
				printf("User %d is now unfollowing user %d\n", tempMsg->UserID, tempMsg->LeaderID);
			}
			
			/* Send confirmation of error */
			if (sendto(sock, echoBuffer, sizeof(echoBuffer), 0, (struct sockaddr *) &echoClntAddr, sizeof(echoClntAddr)) != sizeof(echoBuffer)){
				DieWithError("sendto() sent a different number of bytes than expected");
			}
		}
    }
    /* NOT REACHED (after end of for loop) */
}
