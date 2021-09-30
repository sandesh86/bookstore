# bookstore
Book Store Application (Rest API)

Steps to run application

1 - Download code in local machine

2 - Build Application

    Go to application folder and run command: mvn clean install
    
    It will create bookstore-docker.jar file in target folder
    
3 - Build docker image

    Go to application folder and run command: docker build -f Dockerfile -t bookstore-app .
    
    docker image with name bookstore-app will be created
    
4 - Run application with docker container

    run command: docker run -p 8085:8085 bookstore-app
    
5 - Check API specification on below URL

    http://localhost:8085/swagger-ui.html
    
    You can try out APIs here only or can use Postman
