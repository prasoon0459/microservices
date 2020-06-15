function initiate(){
    $.ajax({'url':"http://10.167.80.144/user",'type': "GET",
    'contentType':"application/json",dataType: "json",
    success: function (response) {
        table.DataTable({
            data: response, 
            columns: [
                {"data":"username"},
                {"data":"name"},
                {"data":"phone"},
                {"data":"password"},
                {"data":"valid"},
                {"data":"roles"}
            ]
        });
    },
    error: function (xhr, status){
        console.log(xhr);
        if(xhr.status==401){
            let refresh_result=refresh();
            console.log(refresh_result);
            if(refresh_result){
                console.log("Token was refreshed");
                initiate();
            }
            else{
                console.log("Token was not refreshed");
            }
        }
    }});
};

function add(){
    let this_role=[];
    if($('#add_adminrole').is(":checked")){
        this_role.push("ROLE_ADMIN");
    }
    if($('#add_userrole').is(":checked")){
        this_role.push("ROLE_USER");
    }
    let this_valid=$('#add_valid').is(":checked");
    $.ajax({url:"http://10.167.80.144/user",'type': "POST",
    data:JSON.stringify({
        username:$('#add_username').val(),
        name:$('#add_name').val(),
        phone:$('#add_phone').val(),
        password:CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
        valid:this_valid,
        roles:this_role}),
        'contentType':"application/json",
        dataType: "json",
        success: function (response){
            console.log(respone);
        },
        error: function (xhr, status){
            if(xhr.status==401){
                if(refresh()){
                    console.log("Token was refreshed");
                    add();
                }
                else{
                    console.log("Token was not refreshed");
                }
            }
        }
    });
    
    table.DataTable().row.add({
        "username":$('#add_username').val(),
        "name":$('#add_name').val(),
        "phone":$('#add_phone').val(),
        "password":CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
        "valid":this_valid,
        "roles":this_role}).draw();
        
        closeadd();
        
    }//end of add
    
    function update(){
        let this_role=[];
        if($('#update_adminrole').is(":checked")){
            this_role.push("ROLE_ADMIN");
        }
        if($('#update_userrole').is(":checked")){
            this_role.push("ROLE_USER");
        }
        let this_valid=$('#update_valid').is(":checked");
        $.ajax({url:"http://10.167.80.144/user",'type': "PUT",
        data:JSON.stringify({
            username:$('#update_username').val(),
            name:$('#update_name').val(),
            phone:$('#update_phone').val(),
            password:CryptoJS.MD5($('#update_password').val()).toString(CryptoJS.enc.Base64),
            valid:this_valid,
            roles:this_role}),
            'contentType':"application/json",
            dataType: "json",
            success: function (response){
                console.log(response);
            },
            error: function (xhr, status){
                console.log(xhr);
                if(xhr.status==401){
                    if(refresh()){
                        console.log("Token was refreshed");
                        update();
                    }
                    else{
                        console.log("Token was not refreshed");
                    }
                }
            }
        });
        table.DataTable().rows( function ( idx, data, node ) {
            return (data.username).equals( $('#update_username').val());
        }).remove().draw();
        table.DataTable().row.add({
            "username":$('#update_username').val(),
            /*"name":$('#update_name').val(),
            "phone":$('#update_phone').val(),
            "password":CryptoJS.MD5($('#update_password').val()).toString(CryptoJS.enc.Base64),
            */"valid":this_valid,
            "roles":this_role.toString()}).draw();
            closeupdate();
            
        }
        
        
        function del(){
            $.ajax({url:"http://10.167.80.144/user/"+$('#delete_username').val(),'type':"DELETE",
            success: function (response){
                var resp = response;
                console.log(resp);
            },
            error: function (xhr, status){
                if(xhr.status==401){
                    if(refresh()){
                        console.log("Token was refreshed");
                        del();
                    }
                    else{
                        console.log("Token was not refreshed");
                    }
                }
            }});
            table.DataTable().rows( function ( idx, data, node ) {
                return (data.username).equals($('#delete_username').val());
            }).remove().draw();
            closedelete();
        }