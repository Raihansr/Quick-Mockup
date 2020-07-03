package com.lict.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Stored_Data {

    public static ArrayList<Integer> Question_Limit_List = new ArrayList<>(Arrays.asList(10, 15, 20, 30, 35, 40, 50));
    public static ArrayList<Integer> Time_Limit_List = new ArrayList<>(Arrays.asList(300000, 600000, 900000, 1200000, 1500000, 1800000, 2400000));
    public static ArrayList<String> Category_List = new ArrayList<>(Arrays.asList("Android", "ICT"));
    public static int Question_Limit = Question_Limit_List.get(0);
    public static long Time_Limit = Time_Limit_List.get(0);
    public static int Warning_Limit = 2;

    public static ArrayList<String> QuizID = new ArrayList<>();
    public static ArrayList<String> Question = new ArrayList<>();
    public static ArrayList<String> Choice_1 = new ArrayList<>();
    public static ArrayList<String> Choice_2 = new ArrayList<>();
    public static ArrayList<String> Choice_3 = new ArrayList<>();
    public static ArrayList<String> Choice_4 = new ArrayList<>();
    public static ArrayList<String> Answer = new ArrayList<>();
    public static ArrayList<String> Explanation = new ArrayList<>();

    public static long Current_Time = 0;
    public static int Current_Question_Answered = 0;

    public static ArrayList<Integer> Random_Questions(){
        ArrayList<Integer> questions = new ArrayList<>();
        Random rand = new Random();
        while (true) {
            int number = rand.nextInt(Question.size());
            if (!questions.contains(number)) {
                questions.add(number);
                if (questions.size() == Question_Limit)
                    break;
            }
        }
        return questions;
    }

    public static String[][] Quizzes = {

            /*{"Question",   "Choice_1",   "Choice_2",   "Choice_3",   "Choice_4",   "Answer",   "Explanation"}*/

            {"Which of the following units represents the largest amount of data?",
                    "Gigabyte",
                    "Terabyte",
                    "Byte",
                    "Megabyte",
                    "B",
                    "Sorry! no explanation"},

            {"Which one is NOT related to fragment class?",
                    "DialogFragment",
                    "ListFragment",
                    "PreferenceFragment",
                    "CursorFragment",
                    "D",
                    "Sorry! no explanation"},

            {"A ___________ makes a specific set of the application data available to other applications.",
                    "Content provider",
                    "Broadcast receivers",
                    "Intent",
                    "None of these",
                    "A",
                    "Sorry! no explanation"},

            {"Which among these are NOT a part of Android’s native libraries?",
                    "Webkit",
                    "Dalvik",
                    "OpenGL",
                    "SQLite",
                    "B",
                    "Sorry! no explanation"},

            {"Generally collection of Nodes is called as __________.",
                    "Heap",
                    "Linked List",
                    "Stack",
                    "Pointer",
                    "B",
                    "Sorry! no explanation"},

            {"What Activity method you use to retrieve a reference to an Android view by using the id attribute of a resource XML?",
                    "onCreate",
                    "onSelect",
                    "onClick",
                    "ContentResolver",
                    "D",
                    "Sorry! no explanation"},

            {"The requests from Content Provider class is handled by method?",
                    "findViewByReference(int id);",
                    "findViewById(int id)",
                    "retrieveResourceById(int id)",
                    "findViewById(String id)",
                    "B",
                    "Sorry! no explanation"},

            {"When contentProvider would be activated?",
                    "Using Intent",
                    "Using SQLite",
                    "Using ContentResolver",
                    "None",
                    "C",
                    "Sorry! no explanation"},

            {"Which company developed android?",
                    "Android Inc",
                    "Google",
                    "Apple",
                    "Nokia",
                    "A",
                    "Sorry! no explanation"},

            {"Linked list is generally considered as an example of _________ type of memory allocation.",
                    "Static",
                    "Compile Time",
                    "Dynamic",
                    "None of these",
                    "C",
                    "Sorry! no explanation"},

            {"During an Activity life-cycle, what is the first callback method invoked by the system?",
                    "onStop()",
                    "onStart()",
                    "onCreate()",
                    "onRestore()",
                    "C",
                    "Sorry! no explanation"},

            {"What does the src folder contain?",
                    "Image and icon files",
                    "XML resource files",
                    "The application manifest file",
                    "Java source code files",
                    "D",
                    "Sorry! no explanation"},

            {"What are the indirect Direct subclasses of Services?",
                    "InputMethodService",
                    "RemoteViewsService",
                    "SpellCheckerService",
                    "RecognitionService",
                    "A",
                    "Sorry! no explanation"},

            {"Android is based on Linux for the following reason.",
                    "Security",
                    "Portability",
                    "Networking",
                    "All of these",
                    "D",
                    "Sorry! no explanation"},

            {"When developing for the Android OS, Java bytecode is compiled into what?",
                    "Java source code",
                    "Dalvik application code",
                    "Dalvik byte code",
                    "C source code",
                    "C",
                    "Sorry! no explanation"},

            {"An activity can be thought of as corresponding to what?",
                    "A Java project",
                    "A Java class",
                    "A method call", "An object field",
                    "B",
                    "Sorry! no explanation"},

            {"DVM is developed by _________.",
                    "Linus Torvald",
                    "Dennis Ritchie",
                    "Dan Bornstein",
                    "None of these",
                    "C",
                    "Sorry! no explanation"},

            {"Which one is not a nickname of a version of Android?",
                    "Honeycomb",
                    "Cupcake",
                    "Muffin",
                    "Gingerbread",
                    "C",
                    "Sorry! no explanation"},

            {"What is the name of the program that converts Java byte code into Dalvik byte code?",
                    "Android Interpretive Compiler (AIC)",
                    "Dalvik Converter",
                    "Dex compiler",
                    "Mobile Interpretive Compiler (MIC)",
                    "C",
                    "Sorry! no explanation"},

            {"What was the first phone released that ran the Android OS?",
                    "T-Mobile G1",
                    "Google gPhone",
                    "HTC Hero",
                    "Motorola Droid",
                    "A",
                    "Sorry! no explanation"},

            {"What is a context in android?",
                    "It is an interface to store global information about an application",
                    "It is used to create new components",
                    "Android has two contexts,those are getContext() and getApplicationContext()",
                    "All of Above",
                    "D",
                    "Context is used to create new components or objects like views and it is used to start activity and services. Android has two kinds of contexts and those are getContext() and getApplicationContext()."},

            {"Which of the following is/are are the subclasses in Android?",
                    "Action Bar Activity",
                    "Preference Activity",
                    "Tab Activity",
                    "All of Above",
                    "D",
                    "Action bar,Launcher, Preference and Tab activities are subclasses of activities in android"},

            {"What are the return values of onStartCommand() in android services?",
                    "START_STICKY",
                    "START_NOT_STICKY",
                    "START_REDELIVER_INTENT",
                    "All of Above",
                    "D",
                    "START_STICKY − If android stops services forcefully, using with START_STICKY, it can be restarted automatically without the user interaction.\n" +
                            "\n" +
                            "START_NOT_STICKY − If android stops services forcefully, it will not restart services till user start services.\n" +
                            "\n" +
                            "START_REDELIVER_INTENT − If android stops services forcefully, it will restart services by re-sending an intent."},

            {"What is the life cycle of broadcast receivers in android?",
                    "send intent()",
                    "onRecieve()",
                    "implicitBroadcast()",
                    "sendBroadcast(), sendOrderBroadcast(), and sendStickyBroadcast().",
                    "B",
                    "Broadcast receiver has only onReceive() method. Broadcast starts from onRecieve() and control comes out from onRecieve()."},

            {"How to get current location in android?",
                    "Using with GPRS",
                    "Using location provider",
                    "Network servers",
                    "A & B",
                    "D",
                    "GPRS and Location provider is used to fetch the current location of a phone as longitude and latitude."},

            {"What is breakpoint in android?",
                    "Breaks the application",
                    "Breaks the development code",
                    "Breaks the execution",
                    "None of the above",
                    "C",
                    "Breaks the execution to find the debug value, It is one of the debugging techniques."},

            {"What is an HTTP client class in android?",
                    "http request(get/post) and returns response from the server",
                    "Cookies management",
                    "Authentication management",
                    "None of the above",
                    "A",
                    "Http request has get and post methods and it returns the response from the servers."},

            {"How to find the JSON element length in android JSON?",
                    "length()",
                    "sum()",
                    "add()",
                    "count()",
                    "A",
                    "Using length(), we can find the number of elements are in JSON."},

            {"How many protection levels are available in the android permission tag?",
                    "There are no permission tags available in android",
                    "Normal, kernel, application",
                    "Normal, dangerous, signature, and signatureOrsystem",
                    "None of the above",
                    "C",
                    "Android is having four levels of protection in android permission tag. They are normal, dangerous, signature, and signatureOrsystem."},

            {"Can a class be immutable in android?",
                    "No, it can't",
                    "Yes, Class can be immutable",
                    "Can't make the class as final class",
                    "None of the above",
                    "B",
                    "Class can be immutable."},

            {"Once installed on a device, each Android application lives in_______?",
                    "device memory",
                    "external memory",
                    "security sandbox",
                    "None of the above",
                    "C",
                    "Sorry! no explanation"},

            {"Parent class of Activity?",
                    "Object",
                    "Context",
                    "ActivityGroup",
                    "ContextThemeWrapper",
                    "D",
                    "Sorry! no explanation"},

            {"What are the Direct subclasses of Activity?",
                    "ListActivity",
                    "AccountAuthenticatorActivity",
                    "FragmentActivity",
                    "All of the aove",
                    "D",
                    "Sorry! no explanation"},

            {"Parent class of Service?",
                    "Object",
                    "ContextWrapper",
                    "Context",
                    "ContextThemeWrapper",
                    "B",
                    "Sorry! no explanation"},

            {"When contentProvider would be activated?",
                    "Using Intent",
                    "Using SQLite",
                    "Using ContentResolver",
                    "None of the above",
                    "C",
                    "Sorry! no explanation"},

            {"Which of the  important device characteristics that you should consider as you design and develop your application?",
                    "Using Intent",
                    "Using SQLite",
                    "Device features",
                    "All of the above",
                    "D",
                    "Sorry! no explanation"},

            {"Which are the screen sizes in Android?",
                    "Small",
                    "Normal",
                    "Large",
                    "All of the above",
                    "D",
                    "Sorry! no explanation"},

            {"Which are the  screen densities in Android?",
                    "Low density",
                    "Medium density",
                    "High density",
                    "All of the above",
                    "D",
                    "Sorry! no explanation"},

            {"You can shut down an activity by calling its _______ method.",
                    "finish()",
                    "finishActivity()",
                    "onDestory()",
                    "None of the above",
                    "A",
                    "Sorry! no explanation"},

            {"Definition of Loader?",
                    "loaders make it easy to asynchronously load data in an activity or fragment",
                    "loaders make it easy to synchronously load data in an activity or fragment",
                    "loaders does not make it easy to asynchronously load data in an activity or fragment",
                    "None of the above.",
                    "A",
                    "Sorry! no explanation"},

            {"Characteristics of the  Loaders?",
                    "They are available to every Activity and Fragment",
                    "They provide asynchronous loading of data",
                    "They monitor the source of their data and deliver new results when the content changes",
                    "All of the above",
                    "D",
                    "Sorry! no explanation"},

            {"How many ways to start services?",
                    "Started",
                    "Bound",
                    "A & B",
                    "None of the above",
                    "C",
                    "Sorry! no explanation"},

            {"If your service is private to your own application and runs in the same process as the client (which is common), you should create your interface by extending the ________class?",
                    "Binder",
                    "Messenger",
                    "AIDL",
                    "None of the above",
                    "A",
                    "Sorry! no explanation"},

            {"If you need your interface to work across different processes, you can create an interface for the service with a ________?",
                    "Messenger",
                    "Binder",
                    "AIDL",
                    "A or C",
                    "D",
                    "Sorry! no explanation"},

            {"Layouts in android?",
                    "Frame Layout",
                    "Linear Layout",
                    "Relative Layout",
                    "All of the above",
                    "D",
                    "Sorry! no explanation"},

            {"If you want share the data accross the all applications ,you should go for?",
                    "Content Provider",
                    "Internal Storage",
                    "SQLite Databases",
                    "Shared Preferences",
                    "A",
                    "Sorry! no explanation"},

            {"Difference between android api and google api?",
                    "The google API includes Google Maps and other Google-specific libraries. The Android one only includes core android libraries",
                    "The google API one only includes core android libraries. The Android  includes Google Maps and other Google-specific libraries",
                    "None of the above.",
                    "All of the above.",
                    "A",
                    "Sorry! no explanation"},

            {"Which configuration file holds the permission to use the internet?",
                    "Layout file",
                    "Manifest file",
                    "Java source file",
                    "Property file",
                    "B",
                    "Sorry! no explanation"},

            {"Which of these is not defined as a process state?",
                    "Non-visible",
                    "Visible",
                    "Foreground",
                    "Background",
                    "A",
                    "Sorry! no explanation"},

            {"What is a correct statement about an XML layout file?",
                    "A layout PNG image file",
                    "A file used to draw the content of an Activity",
                    "A file that contains all application permission information",
                    "A file that contains a single activity widget.",
                    "B",
                    "Sorry! no explanation"},

            {"Which is not included in the Android application framework?",
                    "WindowManager",
                    "NotificationManager",
                    "DialerManager",
                    "PackageManager",
                    "C",
                    "Sorry! no explanation"},

            {"What Eclipse plugin is required to develop Android application?",
                    "J2EE",
                    "Android Software Development Kit",
                    "Android Development Tools",
                    "Web Development Tools",
                    "C",
                    "Sorry! no explanation"},

            {"Which of these files contains text values that you can use in your application?",
                    "AndroidManifest.xml",
                    "res/Text.xml",
                    "res/layout/Main.xml",
                    "res/values/strings.xml",
                    "D",
                    "Sorry! no explanation"},

            {"Which of the following is not an Activity lifecycle call-back method?",
                    "onStart",
                    "onBackPressed",
                    "onCreate",
                    "onPause",
                    "B",
                    "Sorry! no explanation"},

            {"Which of the following is a correct Android Manifest statement?",
                    "<uses-permission android:name =”android.Internet”/>",
                    "<uses-permission android:name =”android.Internet”></uses-permission>",
                    "<uses-permission android:name =”android.permission.Internet”>",
                    "<uses-permission android:name =”android. permission.Internet”/>",
                    "D",
                    "Sorry! no explanation"},

            {"Which of the following tools dumps system log messages including stack traces when the device or emulator throws an error?",
                    "DDMS",
                    "Logcat",
                    "ADB",
                    "Console",
                    "B",
                    "Sorry! no explanation"},

            {"How to enable JavaScript in WebView?",
                    "myWebView. setJavaScriptEnabled(true);",
                    "myWebView.getJavaScriptSettings.setEnabled(true)",
                    "myWebView.getSettings().setJavaScriptEnabled(true);",
                    "Java script is always enabled in WebView",
                    "C",
                    "Sorry! no explanation"},

            {"What does the following code achieve?\n" + "Intent intent = new Intent(FirstActivity.this, SecondActivity.class);\n" + "startActivityForResult(intent);",
                    "Starts a browser activity",
                    "Starts a sub-activity",
                    "Starts an activity service",
                    "Sends results to another activity",
                    "B",
                    "Sorry! no explanation"},

            {"Which of the following is NOT true about the MenuItem interface?",
                    "The MenuItem instance will be returned by the Menu class add(...) method",
                    "MenuItem can decide the Intent issued when clicking menu components",
                    "MenuItem can display either an icon or text",
                    "MenuItem can set a checkbox",
                    "B",
                    "Sorry! no explanation"},

            {"Which of the following is not a ContentProvider provided natively by Android?",
                    "The contacts list",
                    "The telephone log",
                    "The bookmarks",
                    "The application list",
                    "D",
                    "Sorry! no explanation"}

            /*{"Question",
                    "option_1",
                    "option_2",
                    "option_3",
                    "option_4",
                    "Answer",
                    "Sorry! no explanation"},*/
    };
}
