# SpringBootApp
Application on File Metadata upload with Springboot

Requirements-
Implement a RESTFul API spring-boot application that provides the following APIs:

• API to upload a file with a few meta-data fields. Persist meta-data in persistence store (In-memory DB or file system and store the content on a file system)

• API to get file meta-data

• API to download content stream (Optional)

• API to search for file IDs with a search criterion (Optional)

• Write a scheduler in the same app to poll for new items in the last hour and send an email (Optional)

Pre-Requisites-

Set the below propertied in application.properties file, to specify the default path for file upload in system
file.upload.path=/Users/pink/uploads



 # Implementations
 File Id is generated as UUID for each file record.
 User can upload one or multiple files of specific types( "pdf", "jpeg", "jpg", "doc", "docx"), which is configurable and read from properties file.
 User can provide custom path to get file uploaded from request param 
 User can search file with filename containing a specific search string, i.e- like %search% is implemented
 User can search file with fileId as uuid 
 user can download file with fileId.
 
 

## API to upload a file with a few meta-data fields. Persist meta-data in persistence store (In-memory DB or file system and store the content on a file system)
1. Upload one or multiple files -
  http://localhost:8081/file-upload/api/uploadFiles
 

## API to get file meta-data
2. http://localhost:8081/file-upload/api/getAllFilesMetaData

## API to search for file IDs with a search criterion (Optional) 
3. http://localhost:8081/file-upload/api/getAllFilesMetaData?fileName=AIT

4. http://localhost:8081/file-upload/api/getAllFilesMetaData?fileId=fileId

5. http://localhost:8081/file-upload/api/getAllFilesMetaData?pageNo=0&pageSize=5&sortKey=crtTs&sortOrder=ASC&fileName=signed

##  API to download content stream (Optional)

6. http://localhost:8081/file-upload/api//downloadFile/{fileId}

## Scheduler implemented to execute the task - implementation pending to search all files with in specific time and send alerts via email using theamlyf framework.



http://localhost:8081/file-upload/swagger-ui.html( required more improvements to make it workable)
