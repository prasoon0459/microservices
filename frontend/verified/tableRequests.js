function initiate(){
    $.ajax({'url':"http://bits.poc.com/user",'type': "GET",
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
    $.ajax({url:"http://bits.poc.com/user",'type': "POST",
    data:JSON.stringify({
        username:$('#add_username').val(),
        name:$('#add_name').val(),
        phone:$('#add_phone').val(),
        password:CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
        valid:this_valid,
        roles:this_role}),
        'contentType':"application/json",
        success: function (response){
            if(response!= "Added"){
                alert(response);
            }
            else{
                table.DataTable().row.add({
                    "username":$('#add_username').val(),
                    "name":$('#add_name').val(),
                    "phone":$('#add_phone').val(),
                    "password":CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
                    "valid":this_valid,
                    "roles":this_role}).draw();
                    
                    closeadd();
                }
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
        $.ajax({url:"http://bits.poc.com/user",'type': "PUT",
        data:JSON.stringify({
            username:$('#update_username').val(),
            name:$('#update_name').val(),
            phone:$('#update_phone').val(),
            password:CryptoJS.MD5($('#update_password').val()).toString(CryptoJS.enc.Base64),
            valid:this_valid,
            roles:this_role}),
            'contentType':"application/json",
            success: function (response){
                if(response!="Updated"){
                    alert(response);
                }
                else{
                    table.DataTable().rows( function ( idx, data, node ) {
                        return (data.username)==( $('#update_username').val());
                    }).remove().draw();
                    table.DataTable().row.add({
                        "username":$('#update_username').val(),
                        "name":$('#update_name').val(),
                        "phone":$('#update_phone').val(),
                        "password":CryptoJS.MD5($('#update_password').val()).toString(CryptoJS.enc.Base64),
                        "valid":this_valid,
                        "roles":this_role.toString()}).draw();
                        closeupdate();
                }
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
        }
        
        
        function del(){
            $.ajax({url:"http://bits.poc.com/user/"+$('#delete_username').val(),'type':"DELETE",
            success: function (response){
                if(response!="Deleted"){
                    alert(response);
                }
                else{
                    table.DataTable().rows( function ( idx, data, node ){
                        return (data.username)==($('#delete_username').val());
                    }).remove().draw();
                    closedelete();
                }
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
        }