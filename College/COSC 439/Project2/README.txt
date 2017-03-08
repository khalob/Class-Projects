1) Log into emunix through your preferred ssh client
2) Upload the contents of this zip file
3) Type the following commands to compile the scripts
	gcc -o client TCPEchoClient.c DieWithError.c 
	gcc -o server TCPEchoServer.c DieWithError.c HandleTCPClient.c

4) Type the following commands to run the compiled scripts
	./server <PortNumber>
	./client <IPofServer> <PortNumber>
	
	ex: ./server 2555
		./client 127.0.0.1 2555
5) Make sure to type in a file name for the server to use. Don't use file extensions. 
	Ex: 11-21-2016
	
6) From there, you are prompted with a list of commands you can type in.
	Ex:keyword fake
	Ex:Keyword
	fake
	
	
I hard coded the locations of the newsfeed files to be in the folder called "News Feed". Keep that in mind.