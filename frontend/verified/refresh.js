function refresh(){
    console.log('refresh called');
    $.ajax({'url':"http://10.167.80.144/auth/refresh",'type': "POST",
     'contentType':"application/json",dataType: "json",
    success: function (response) {
        return true;
    },
    error: function (xhr, status){
        return false;
    }});
}