# Website Builder Inclass Project
## **Developing a Software Engineering Website (iTrust)**


## 1. Basic Logistics and Project Overview
###  **Project Logistics**:
In this project, there are 7 people in my group to finish this whole project. 
</br>
In every iteration (Word **"Iteration"** can be regarded as **"Checkpoint"**), people will have a formal meeing with **manager** and evaluate the progress so far.
</br>
In this project, we use the iteration meeting time as following.

|Iteration Type  | Time           |		
|------------- | -------------| 
|First Meeting  | Oct 17 2019 | 
|Iteration 1  | Oct 31 2019 | 
|Iteration 2  | Nov 14 2019 | 
|Iteration 3 (Final Part)  | Dec 03  2019 |   

</br>
After the initial meeting, we decide to divide our group dynamically according to the strength of group's teammates.
During each iteration, we will distribute the work to each subgroup and aggregate it during the weekly meeting.

###  **Project Overview:**

 **Programming:** *Frontend*: <font color=#ff0000>JavaScript</font>, HTML; *Backend*： SQL, <font color=#ff0000>Java</font>, Python. </br>*<font color = #ff0000>Red</font> shows the mainly used language in a certain programming part.*  
 </br>
 **Coding:**: According to our customer's needs, we generate several **USER STORIES** from them, and we try to implement those user stories during the project period.  
 </br>
 **Refactoring:** During each iteration, one of our team member acted as a supervisor to check the readability and overall structure in the code that we had written so far.

## 2. Project Structure
|User Cases  | Comment           |		
|------------- | -------------| 
|UC 91  | Theme: New patient pre-registration </br> | 
|UC 92  | Theme: Activate pre-registered patient  </br> | 
|UC 30 | Theme: Message Displaying Filter </br>In main flow of UC30, implement the feature described in the sentence "An LHCP or patient/representative can modify and save his/her message displaying filter [S7] or view his/her  message inbox [S5] including only the messages satisfying the specified filtering criteria in the saved filter." Note that the current implementation includes only a simple search box.  Your new feature shall be added to complement (instead of replacing) this simple search box.</br>Note that specification of the starting date and ending date in the filtering criteria needs to adopt using a calendar as already implemented for UC22/S2. </br>| 
|UC 41 | Theme: Reminder </br>  | 
|UC 39 | Theme: View Transaction Logs </br>  | 
|UC 14 | Theme: Request Biosurveillance </br> | 
|UC 20 | 	Theme: View cause-of-death trends report </br>  |         
## 3. Project Goals or Requirements
1. Create JUnit tests for at least 80% statement coverage for each non-GUI class. Incrementally writing JUnit code will motivate you to keep the code that implements logic separate from the code that implements user interface (the MVC pattern!) . **Warning: do NOT complete program and then try to write the JUnit tests -- or you will end up having to rewrite much of the code to make it testable.**
2. Create Selenium tests to automate black box tests that you create for each UC.
3. Implement the code to pass JUnit and Selenium tests for each UC. Below are some tips and guidelines.
* Where possible, try to keep small, common pieces of functionality separate and include as appropriate into larger pieces of functionality. This will help focus testing on small chunks and make it easier to maintain!
4. Ensure all coding adheres to the Java Coding Standard. 
5.  Following a coding standard is just plain good practice.  But, it is especially important to follow a standard when you pair program.  If there is a standard to follow, you can’t “argue” with partner about these types of issues.
3. Follow the code review process.

## 4. Project Activation

**Activation Location:** 
> ~(Your git repo)/iTrust  

**Automatic Building:** By using **MAVEN** in Linux environment  
To install maven, using following instrustion:

```
$ sudo apt-get install maven
```


**Website Manager:** By using **TOMCAT 7**  
In order to **BUILD** the project, we use the following instruction:
```
$ mvn package
```. 

In order to **ACTIVATE** the website, we use the following instruction:
```
$ mvn tomcat7:redeploy
```. 

In order to **CLEAR** the cache in website, we use the following instruction:
```
$ mvn clean
```. 

In order to **TEST** the Junit or Selenium test in the website, we use the following instruction:
```
$ mvn test
```

## 5. Final Thoughts of this Project

 During this project, we met every weeks to check the progress of each group member. Instead of doing everything individually, we divide our group into several small groups, which is an efficient action. More importantly, this project simulates the real office scenarios which provides precious experience before the start of our careers. During this project, I know how to manage a huge project by using Maven. Before I took this course, I have only managed project less than 30 files. However, in this project, hundreds of files are managed successfully by our team. In fact, I learned that cooperation is vital while doing this project. A team should maximize its efficiency by moving every teammates to the right position. Fortunately, I think our group has done it successfully.  
  
   Besides that, I have also learned many practical thing such as integrating SQL into Java project, how to modify the Javascript file and how to draw graphs in the website. Those things will help me a lot in the future.  
   
   In a word, I really enjoy this project, and I really cherish the experience that I have learned during this final project.
  

## 6. LEGAL
-----
Permission to use, copy, modify, and distribute this software and its
documentation for any purpose, without fee, and without written agreement is
hereby granted, provided that the above copyright notice and the following
two paragraphs appear in all copies of this software.

IN NO EVENT SHALL THE AUTHOR OR THE UNIVERSITY OF ILLINOIS BE LIABLE TO
ANY PARTY FOR DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL
DAMAGES ARISING OUT  OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION,
EVEN IF THE AUTHOR AND/OR THE UNIVERSITY OF ILLINOIS HAS BEEN ADVISED
OF THE POSSIBILITY OF SUCH DAMAGE.

THE AUTHOR AND THE UNIVERSITY OF ILLINOIS SPECIFICALLY DISCLAIM ANY
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.  THE SOFTWARE

PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND NEITHER THE AUTHOR NOR
THE UNIVERSITY OF ILLINOIS HAS ANY OBLIGATION TO PROVIDE MAINTENANCE,
SUPPORT, UPDATES, ENHANCEMENTS, OR MODIFICATIONS."



