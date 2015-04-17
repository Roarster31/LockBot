var constants = require('./constants');
var client = require('twilio')(constants.accountSid, constants.authToken);
var express = require('express');
var app = express();

var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost/test');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function (callback) {
  console.log("db connected")
});

var TxtSecret = mongoose.model('TxtSecret', { uuid: String, message: String });

app.get('/voice', function (req, res) {
	
		res.status(200).send('<?xml version="1.0" encoding="UTF-8"?><Response><Say voice="woman">Please speak your personal code after the beep.</Say><Record timeout="10" playBeep="true" transcribe="true" /></Response>');
	
});

app.put('/register', function (req, res) {
	var uuid = req.query.uuid;
	if(uuid){
		console.log("user "+uuid+" registered");
		res.status(200).send('{message:"ok",uuid:'+uuid+'}');
	}else{
		res.status(500).send('{message:"error",uuid:null}');
	}
});

app.post('/requestText', function (req, res) {
	var num = req.query.uuid;
	if(num){
		var secretMessage = makeid();

		var msg = new TxtSecret({ uuid: num, message: secretMessage});
		msg.save(function (err) {
  			if (err) {
  				console.log("err");
  				// res.status(500).send('{message:"error",uuid:null}');
  			}else{
  				console.log("secret message saved for "+num+":"+secretMessage);
  				// res.status(200).send('{message:"ok",uuid:'+num+'}');
  			}
		});

		console.log("sending "+secretMessage+" to "+num);
		client.messages.create({
    		body: secretMessage,
    		to: num,
    		from: constants.PHONE_NUMBER
		}, function(err, message) {
    		console.log(err);
		});
		res.status(200).send('{message:"ok",uuid:'+num+'}');
	}else{
		res.status(500).send('{message:"error"}');
	}
});

app.get('/validateMessage', function (req, res) {
	var uuid = req.query.uuid;
	var msg = req.query.message;

	if(uuid){
	TxtSecret.find({ uuid: uuid, message: msg }, function (err, msgs) {
  	if (err){
  		res.status(403).send('{message:"error",uuid:null}');
  	}else{
  			console.log("secret message found, let's go!");
  			res.status(200).send('{message:"ok",uuid:'+uuid+'}');
	}
	});
	}else{
			res.status(403).send('{message:"error",uuid:null}');
		}

	
});

app.get('/requestPhoneNumber', function (req, res) {
	res.status(200).send('{number:'+constants.PHONE_NUMBER+'}');
});

function makeid()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 30; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

var server = app.listen(8080, function () {

  var host = server.address().address;
  var port = server.address().port;

  console.log('Example app listening at http://%s:%s', host, port);

});