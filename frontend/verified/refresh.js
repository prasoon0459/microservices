function refresh(){
    console.log('refresh called');
    $.ajax({'url':"http://10.167.80.144/auth/refresh",'type': "POST",
    'data': JSON.stringify({refresh_token:Cookies.get('refresh_token')}),
    'contentType':"application/json",dataType: "json",
    success: function (response) {
        Cookies.set('access_token',response.access_token,{expires:(response.expiry/86400000)});
        return true;
    },
    error: function (xhr, status){
        return false;
    }});
}