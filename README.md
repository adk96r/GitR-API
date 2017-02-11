# GitR-API
This repo is for all the students of GITAM who want to automate things they normally do on XLearn . This is basically a quick little scrapper ! The main package used for scrapping is jsoup . Definitely check it out , the link is at the end of this document.

# How to use this API ?

Before you can see the your data fromyou X-Learn you must ofcourse login first . This can be done by :
```java
   int status = login(username, password);
```
where **username** and **password** are **Strings** . The specification for every method is specified at the end of this doc.
If the **status** is 1, we have a successful login and all our data is stored in a local object called **myGit** . This is the basically a bundle having all out data . So from now onwards we will only use this object .

##Using myGit :
This will give you a glimpse of what you can do with the data this GitR is scrapping .
 ```java
    private static void show(MyGit myGit){
        
        for (Course each : myGit.courses()){
            System.out.println("Course : " + each.getTitle());            
            
            if(each.hasAssignments()){
                System.out.println("This course has" + each.getAssignments().size() + " assignments.");
                for(int i = 0;i<each.getAssignments().size();i++){
                    System.out.println(each.getAssignments().get(i).getCourseName() + "  " + each.getAssignments().get(i).getDetails());
                }
            }
            
            System.out.println("Attendance for this course is " + each.getAttendance());
        }
    }
```

### Course Class:
  1. Course( String title, Float attendance, List<< Assignment >> assignments, Content[] content )
  2. String getTite()
  3. boolean hasAssignments()
  4. boolean hasContent()
  5. List<< Assignment >> getAssignments()
  6. Float getAttendance()
 
### Assignment Class:
  1. Assignment( String courseName, String details, String link)
  2. String getCourseName()
  3. String getDetails()
  4. String getLink()

### Content Class:
  1. Content( String name, String link) 
  2. String getName()
  3. String getLink()

### AttendanceData:
  1. AttendanceData( String attDate, String attTime, String attStatus)
  2. String getData()
  3. String getTime()
  4. String getStatus()

The data is stored in form of these **primary blocks** .

  
## Threading :
  While scrapping , one can use simple loop to go through every course and get its attendance, resources and assignments , but when done in such a way , the whole process would take about 50 seconds ( For an average of 8 courses ).
Hence, in this API, there are lot of threads involved :
- One CourseRetriever thread per course is started in the getCourses()
- Each such thread invoked further invokes 3 threads:
  - One ResourceRetiever Thread
  - One AttendanceRetriever Thread and
  - One AssignmentRetriever Thread
    
Hence a total of 3 threads per course run simultaneously. This may affect the performance a bit but if the conditions are ideal, the time comes down to a mere 6~9 seconds !

The overall thing takes place like this : 
![Threads](https://cloud.githubusercontent.com/assets/19271795/22854391/a5fcac6c-f093-11e6-8948-38a800516606.png)

This is just the very basic stuff you can do with the data you have !
You can use this to develop some cool applications . You can checkout GitX , an android app which uses this API to give the students their details in a classic look. https://play.google.com/store/apps/details?id=sevenfront.gitx

___

I hope the content above makes some sense 😄 . Feel free to use it to build your own applications . I would recommend you to go through the code once ,  its quite easy and understandable . Check this out - [Jsoup](https://jsoup.org/).



