# Methods
- **REGISTER NEW USER** :

   - END POINT : /auth/register
   - METHOD : POST
   - REQUEST: 
	   ```
	   Body: {
		"username":"abcd@xyz.com",
		"password":"123456",
		"name":"prasoon baghel",
		"phone": "1234567890"
	   }
	   ```
   - RESPONSE : 
	   ```
	   Body: {
		"path": "auth/register",
		"error": false,
		"message": "Successfully Registerd!!",
		"timestamp": "2020-06-12T17:52:26.517+00:00",
		"status": 201
		}
	   ```

- **SIGN-IN METHOD** :
   - END POINT : /auth/signin
   - METHOD : POST
   - REQUEST: 
	   ```
	   Body: {
		"username":"abcd@xyz.com",
		"password":"123456"
	   }
	   ```
   - RESPONSE : 
	   ```
	   Body: {
		"access_token": "eyJ0eXBlIjoiand0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhYmNkQHh5ei5jb20iLCJuYW1lIjoicHJhc29vbiBiYWdoZWwiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNTkxOTg1MDczLCJleHAiOjE1OTE5ODg2NzN9.iluYQBQ3NAf7i7BUP9DBt176bIwxONn5nHYsrc5IUQpgKxmZTGfYRg61kTureJrK-Jy8eGbL2lgfy_wPS8hnsRgBhbaxc_Xkh8UNzsUmkI-DkItS_Ip21haFTsPlQfdZAm6bZ3VvKJZqvtRxoll4hm6cpz2fsUYZqXwZj5bVXObhcSso0k4EvsfQbDwCJOCDNwT4I07gjBHA10p5_USt69GhywtUqC2muYLcrMhWqn_-pNQJ9DytySx_qI6NV568OXXrw0Y7DvS_IZ3v0C7BkHTvjt38LahWeeCaf6_H_8ZyjWMqCebleiOG8TUzFaNV9zxlk-HhWbQeRRtr_SuHuQ",
		"refresh_token": "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhYmNkQHh5ei5jb20iLCJpYXQiOjE1OTE5ODUwNzMsImV4cCI6MTU5NzE2OTA3M30.Gu-wB84a6P3zZ9zbUf27m-7cpWg5tAHEbSbWKrSAkTc",
		"path": "auth/signin",
		"expiry": 3600000,
		"timestamp": "2020-06-12T18:04:33.372+00:00",
		"status": 200
		}
	   ```
- **REFRESH METHOD** :
   - END POINT : /auth/refresh
   - METHOD : POST
   - REQUEST: 
	   ```
	   Body: {
		"refresh_token":"eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhYmNkQHh5ei5jb20iLCJpYXQiOjE1OTE5ODUwNzMsImV4cCI6MTU5NzE2OTA3M30.Gu-wB84a6P3zZ9zbUf27m-7cpWg5tAHEbSbWKrSAkTc"
	   }
	   ```
   - RESPONSE : 
	   ```
	   Body:{
		"access_token": "eyJ0eXBlIjoiand0IiwiYWxnIjoiUlMyNTYifQ.eyJzdWIiOiJhYmNkQHh5ei5jb20iLCJuYW1lIjoicHJhc29vbiBiYWdoZWwiLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNTkxOTg1MzEwLCJleHAiOjE1OTE5ODg5MTB9.Yz7eidEcSZ8A7jNBnl-WIgkFxyEacFabbhU9lpzFNHbb-9XmHzbxlrdPhUk0Rb2W5520wcUD7fLDwc_KAYyiJRxZD6jxmeBPwIA0oAccwb-WzzC-CAbeBRAl-6CyQPVUo4LBkDVYFAMmQaYANkMfsiCSefvI5M8dX4gvS7NRoPSy9HPblElcWpQ2dPEi8Q_RtyfDRNiH2yYqORBgNdj2DAzvu8Qk6gukj0ZpV2MU3Q-ij7KZFYUIsyD5OY0WzutNHhdtlbLJZU1BFlGjSxARqaj7ibmJFOEZ3VLlKcz9D64c2_mn9euV6ht0UFjWCmVl-cyRLerMEe_Krjs0kUAKTw",
		"refresh_token": "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJzdWIiOiJhYmNkQHh5ei5jb20iLCJpYXQiOjE1OTE5ODUwNzMsImV4cCI6MTU5NzE2OTA3M30.Gu-wB84a6P3zZ9zbUf27m-7cpWg5tAHEbSbWKrSAkTc",
		"path": "auth/refresh",
		"error": false,
		"timestamp": "2020-06-12T18:08:30.393+00:00",
		"status": 201
		}
	```

