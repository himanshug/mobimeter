If you want to find out the network latency from your mobile phone to your HTTP`[S]` server(or any public GET URL) over GPRS/WIFI(or whatever else). You can use this application.

## Prerequisites: ##
A mobile phone capable of running J2ME application with CLDC-1.1 and MIDP-2.0

## How to Install: ##
Download the rar archive available in Downloads, extract the jad and jar files from it and then install it on your phone using the method supported.

## How to Use: ##
Start the app and you see the screen asking for following.

**Warm-Up-Request-Count:** Number of requests to send, just to warm up the server.

**Test-Request-Count:** Number of requests to send for getting latency data.

**Think-Time:** In milliseconds, minimum time between two consecutive requests. I recommend to use something above 30000 ms here because on many devices, HTTP stack implementation caches the response for small time intervals. They get the response directly from the cache(without even sending a request to server) if previous request(sent only a few ms before) is same as current request. In this case response time will be very quick and will corrupt the av. response time reading heavily.

**URL:** the public GET URL

**Show-Response:** If selected, then reads the response into a String(..first ~2000 chars) and displays it in the report. It should be used only to debug and make sure you are getting what you expect in the response.

After you fill up above fields, select "Start" command and next Report screen shows you following in _real time_.

**Status:** It will show one of the following possible values.<br />
_Warming-Up:_ warm up requests are being sent<br />
_In-Progress:_ test requests are being sent<br />
_Finished:_ application has finished collecting the data<br />
_Failed:_ Some exception occurred<br />

**Count:** total test requests sent so far

**Average-Time:** In milliseconds, average time to receive first byte of response

**Min:** In milliseconds, minimum time to receive first byte of response

**Max:** In milliseconds, maximum time to receive first byte of response

**Average-Size:** In bytes, average data received in response

**Errors:** total non HTTP-200 responses received so far



---



## How to Build: ##
If for some reason, you want to build the jar from source code, get it using svn, simply open the project on netbeans(installed with J2ME support) and you're ready to hack. If you can't use netbeans then just pick up the java source files and edit in your favorite editor and use your favorite method of building a J2ME project.