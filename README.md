LockBot
===================


LockBot is your personal security companion I made for a [SimpleWeb](http://simpleweb.co.uk/) hackathon. Hide all your secrets away inside it, and they'll only be accessible on **your** phone with **your** voice. Or so the story was told.

>**Note**
In actual fact I didn't get around to voice authentication - it seems to be a lot tricker than I could have ever anticipated, and definitely not doable in a 3 hour hackathon.

With my failures & excuses out of the way now, I can explain how it all works.

The server is written in [Node.js](https://nodejs.org/), uses [Express.js](http://expressjs.com/) to route requests, [MongoDB](https://www.mongodb.org/)(along with [Mongoose.js](http://mongoosejs.com/)) for some data persistence and [Twilio](https://www.twilio.com/) to interact with the LockBot app. Once it's up and running, the LockBot app will try to register itself with the server.

Once registered you then have to pass LockBot's own "2 factor authentication". First you need to prove that your phone is in fact the correct phone number for the account, and secondly you must verify your voice is correct.

To prove your number LockBot will request the server to send a unique message to the registered phone number. The server generates a secret message, stores it to MongoDB and sends the message to the phone number. Lockbot receives this message, sends it abck to the server and the server confirms whether the secret is correct. Simple!

To verify your voice, LockBot calls a Twilio number provided by the server, which records your voice patterns and magically confirms it's really you speaking.

>We may just let you in after the phone call currently, but no one has to know that now do they ;) It can be our little secret reader...


The Nitty Gritty
-------------

So, maybe you want to know more about how it all works, maybe you don't, you'll find out anyway. Clone this repo and have a little fiddle yourself while I explain how it all runs.

Like I said earlier the server uses [Node.js](https://nodejs.org/), [Express.js](http://expressjs.com/), [MongoDB](https://www.mongodb.org/), [Mongoose.js](http://mongoosejs.com/) & [Twilio](https://www.twilio.com/). So you'll need to install all those things to run the server.

Then you need to start your mongoDB database. I used a custom db directory here, so when in the server dir execute

```mongod --dbpath db```

Once that's done you should configure your Twilio constants in `constants.js`. You'll need your accountSid, authToken & your Twilio phone number of choice. Once that's done you can launch the server with 

```node webserver.js```

You might also like to use ngrok or something similar to make the address accessible externally. You then need to configure [NetworkHelper](app/app/src/main/java/com/smithyproductions/lockbot/network/NetworkHelper.java#L12) with your correct server address.

Once you've configured the address you can build the app and launch it on a device - you will need to use it on a phone with a phone number.

The rest just works!

----------

Feel free to fork me if you can get voice authentication working, that would be **totally badass!!**