// Empty JS for your own code to be here

function search() {
    var entity = document.getElementById("entity");
    var selectedEntity = entity.options[entity.selectedIndex].value;
    var attribute = document.getElementById("attribute");
    var selectedAttribute = attribute.options[attribute.selectedIndex].value;

    var insertedText = document.getElementById("in");

    //alert(selectedEntity + selectedAttribute + " " + insertedText.value);

    var div = document.getElementById("images");
    var input = document.getElementById("in");

    //$("#images").text("dsa");
    alert(input.text);
    div.innerHTML = "dasdas";
}

function handleInput() {
    alert("Handled");
    var div = document.getElementById("images");

    div.innerHTML = div.innerHTML + 'Extra Stuff';
}


function login_by_email() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    if (validation()) // Calling validation function
    {
        //var x = document.getElementsByName('login');
        //x[0].submit(); //form submission
        //alert(" Email : " + email + " \n Password : " + password + " \n Form Name : " + document.getElementById("login").getAttribute("email") + "\n\n Form Submitted Successfully......");

        redirectToMainPage();
    }
}

function validation() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    if (email === '' || password === '') {
        alert("Please fill all fields...!!!!!!");
        return false;
    } else if (!(email).match(emailReg)) {
        alert("Invalid Email!!");
        return false;
    } else if (!(password === '1234')) {
        alert("Wrong password!!");
        return false;
    } else {
        return true;
    }
}

function redirectToMainPage() {
    window.location = "mainIndex.html";
}

$("form").submit(function (event) {
    if ($("input:first").val() === "c") {
        //$(document).ready(function () {
        //    var data = [["City 1", "City 2", "City 3"], //headers
        //        ["New York", "LA", "Seattle"],
        //        ["Paris", "Milan", "Rome"],
        //        ["Pittsburg", "Wichita", "Boise"]]
        //    var cityTable = makeTable($(document.body), data);
        //    event.preventDefault();
        //});

        //$.get("/alive", {"choices[]": ["Jon"]}, function (data) {
        //    $(".result").html(data);
        //    alert("Load was performed.");
        //});
        //);
        //$.post("/insertElement",
        //    {item:item.value});
        $.ajax({
            type: 'POST',
            // make sure you respect the same origin policy with this url:
            // http://en.wikipedia.org/wiki/Same_origin_policy
            url: 'superman',
            data: {
                'foo': 'bar',
                'calibri': 'nolibri'
            },
            success: function(msg){
                alert('wow' + msg);
            },
            failure: function(msg){
                alert(':(');
            }
        });


    } else {
        $("p").text("Not valid!").show().fadeOut(1000);
        event.preventDefault();
    }
});

function makeTable(container, data) {
    var table = $("<table/>").addClass('CSSTableGenerator');
    $.each(data, function (rowIndex, r) {
        var row = $("<tr/>");
        $.each(r, function (colIndex, c) {
            row.append($("<t" + (rowIndex == 0 ? "h" : "d") + "/>").text(c));
        });
        table.append(row);
    });
    return container.append(table);
}

function appendTableColumn(table, rowData) {
    var lastRow = $('<tr/>').appendTo(table.find('tbody:last'));
    $.each(rowData, function (colIndex, c) {
        lastRow.append($('<td/>').text(c));
    });

    return lastRow;
}