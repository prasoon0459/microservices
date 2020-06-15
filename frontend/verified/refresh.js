function refresh(){
    console.log('refresh called');
    $.ajax({'url':"http://10.167.80.144/auth/refresh",'type': "POST",
     'contentType':"application/json",dataType: "json",
    success: function (response) {
        sessionStorage.setItem("response_username",response.username);
        sessionStorage.setItem("response_phone",response.phone);
        sessionStorage.setItem("response_username",response.username);
        sessionStorage.setItem("response_roles",response.roles[0]);
        console.log(response.roles[0]);
        console.log(response.roles[1]);
        console.log('refresh success')
        return true;
    },
    error: function (xhr, status){
        console.log('refresh failed');
        return false;
    }});
}