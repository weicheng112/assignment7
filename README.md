# assignment7
using user class to run this code
the more detialed explaination is above the class

Normally, all the test and main work!
But there's sometime has an error about: [Fatal Error] :1:1: Content is not allowed in prolog.
                                          XML parsing errororg.xml.sax.SAXParseException; lineNumber: 1; columnNumber: 1; Content is not allowed in prolog.
                                          
It seems to me that it might be different encoding format(UTF-8 and UTF-16). I had tried lots of method in Stackoverflow, but the code works and sometimes not.
Also, there's the same problem in the RestExamples. I had also checked out whether there's content before prolog. 


image is about "searchForSong" "searchForArtist" "searchForAlbum" in the user class

![image](https://user-images.githubusercontent.com/108167692/208228714-cbb60179-5581-4a03-a8f1-1e5d29d897f4.jpg)


<updated Dec 22>
finally fix the xml problem!!!
we should make sure that the inputStream need to be xml format. I created a new test(RestExampleTest) amd noticed that if I didn't make "hardcode" format like: &fmt=xml in the end of the url. The inputStream would become json format randomly. That's the reason why the problem:  
"org.xml.sax.SAXParseException: Content is not allowed in prolog" appear randomly.....  
I will show the picture below.
![assignment7](https://user-images.githubusercontent.com/108167692/209095450-cabe608c-0862-4647-8746-e7ba27a9728a.jpg)

