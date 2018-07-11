
# SpringStateMachineToDoList
Java- Spring State Machine

# About
This is a simple example to use Spring State Machine and to create To Do List out of it. It is build on</br>
1.Spring Boot</br>
2.Gradle (To build and manage dependencies)</br>
3.Redis (Caching just for saving List Items)</br>

# To Do List
Its a basic To Do list created for:</br>
1.Creating Note</br>
2.Viewing Note</br>
3.Deleting Note</br>
4.Undo Deleted Note</br>

# Configuration
We have use Spring state machine to create configuration to make this State Driven.In this We have made Staes and Events configure the System.

# Postman 
Save Request:</br>
Url: http://localhost:9090/to-do/save-note </br>
  {</br>
	   "title":"My First Note",</br>
  	 "message":"Low on Words, Need PowerUp.",</br>
     "type":"PERSONAL"</br>
   }</br>
   
View Request: Get URL: http://localhost:9090/to-do/view-note/PERSONAL/My First Note </br>
Edit Request: Get URL: http://localhost:9090/to-do/edit-note/PERSONAL/My First Note </br>
Delete Request: Delete URL: http://localhost:9090/to-do/delete-note/PERSONAL/My First Note </br>


