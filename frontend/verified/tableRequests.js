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
        console.log(status);
        if(refresh()){
            console.log("Token was refreshed");
            initiate();
        }
        else{
            console.log("Token was not refreshed");
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
            console.log(status);
            if(refresh()){
                console.log("Token was refreshed");
                add();
            }
            else{
                console.log("Token was not refreshed");
            }
        }});
        
        table.DataTable().row.add({
            "username":$('#add_username').val(),
            "name":$('#add_name').val(),
            "phone":$('#add_phone').val(),
            "password":CryptoJS.MD5($('#add_password').val()).toString(CryptoJS.enc.Base64),
            "valid":$('#add_valid').val(),
            "roles":this_role.toString()}).draw();
            
            closeadd();
            
        }//end of add
        
        function update(){
            let this_role=[];
            if($('#add_adminrole').val()){
                this_role.push("ROLE_ADMIN");
            }
            if($('#add_userrole').val()){
                this_role.push("ROLE_USER");
            }
            $.ajax({url:"http://10.167.80.144/user",'type': "PUT",
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
                    console.log(status);
                    if(refresh()){
                        console.log("Token was refreshed");
                        add();
                    }
                    else{
                        console.log("Token was not refreshed");
                    }
                }});
                initiate();
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
                    refresh();
                    if(refresh_success){
                        console.log("Token was refreshed");
                        del();
                    }
                    else{
                        console.log("Token was not refreshed");
                    }
                }});
                closedelete();
                initiate();
                //https://stackoverflow.com/questions/38392464/how-to-find-a-specific-row-by-values-in-jquery-datatables
            }