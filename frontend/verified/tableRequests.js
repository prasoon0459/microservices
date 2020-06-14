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
            if(refresh()){
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
    if($('#add_adminrole').val()){
        this_role.push("ROLE_ADMIN");
    }
    if($('#add_userrole').val()){
        this_role.push("ROLE_USER");
    }
    let this_valid;
    if($('#add_valid').is(":checked")){
        this_valid=true;
    }
    else{
        this_valid=false;
    };
    $.ajax({url:"http://10.167.80.144/user",'type': "POST",
    data:JSON.stringify({
        username:$('#add_username').val(),
        name:$('#add_name').val(),
        phone:$('#add_phone').val(),
        password:CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
        valid:$('#add_valid').val(),
        role:this_role.toString()}),
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
        "roles":this_role.toString()}).draw();
        
        closeadd();
        
    }//end of add
    
    function update(){
        let this_role=[];
        if($('#update_adminrole').val()){
            this_role.push("ROLE_ADMIN");
        }
        if($('#update_userrole').val()){
            this_role.push("ROLE_USER");
        }
        let this_valid;
        if($('#update_valid').is(":checked")){
            this_valid=true;
        }
        else{
            this_valid=false;
        };
        $.ajax({url:"http://10.167.80.144/user",'type': "PUT",
        data:JSON.stringify({
            username:$('#update_username').val(),
            name:$('#update_name').val(),
            phone:$('#update_phone').val(),
            password:CryptoJS.MD5($('#update_password').val()).toString(CryptoJS.enc.Base64),
            valid:this_valid,
            role:this_role.toString()}),
            'contentType':"application/json",
            dataType: "json",
            success: function (response){
                console.log(respone);
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
        closeupdate();
        //https://stackoverflow.com/questions/38392464/how-to-find-a-specific-row-by-values-in-jquery-datatables
    }
    
    
    function del(){
        $.ajax({url:"http://10.167.80.144/user/"+$('#delete_username').val(),'type':"DELETE",
        success: function (response){
            var resp = response;
            console.log(resp);
        },
        error: function (xhr, status){
            if(xhr.status==401){
            refresh();
            if(refresh_success){
                console.log("Token was refreshed");
                del();
            }
            else{
                console.log("Token was not refreshed");
            }
        }
        }});
        closedelete();
        console.log(row(search($('#delete_username').val())));
        //table.row(search($('#delete_username').val())).remove().draw();
        //https://stackoverflow.com/questions/38392464/how-to-find-a-specific-row-by-values-in-jquery-datatables
    }