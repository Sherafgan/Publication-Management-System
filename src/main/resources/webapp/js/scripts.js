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
    var entity = document.getElementById("entity");
    var selectedEntity = entity.options[entity.selectedIndex].value;
    var attribute = document.getElementById("attribute");
    var selectedAttribute = attribute.options[attribute.selectedIndex].value;
    var textEdit = document.getElementById("in");
    var searchText = textEdit.value;
    if (selectedEntity == 1) {
        $.ajax({
            type: "GET",
            url: "author",
            //dataType: 'json',
            data: {attribute: selectedAttribute, value: searchText}
        })
            .done(function (msg) {
                showResponse1(msg);
            })
            .fail(function (xhr, status, errorThrown) {
                alert("Sorry. Server unavailable. ");
            });
    }
    else if (selectedEntity == 2) {
        $.ajax({
            type: "GET",
            url: "pub",
            //dataType: 'json',
            data: {attribute: selectedAttribute, value: searchText}
        })
            .done(function (msg) {
                showResponse2(msg);
            })
            .fail(function (xhr, status, errorThrown) {
                alert("Sorry. Server unavailable. ");
            });
    }
    //if ($("input:first").val() === "c") {
    //    $.ajax({
    //        type: "GET",
    //        url: "pub/",
    //        //dataType: 'json',
    //        data: {attribute: }
    //    })
    //        .done(function (msg) {
    //            showResponse(msg);
    //        })
    //        .fail(function (xhr, status, errorThrown) {
    //            alert("Sorry. Server unavailable. ");
    //        });
    //}
    else {
        $("p").text("Not valid!").show().fadeOut(1000);
        event.preventDefault();
    }
});

function showResponse1(resp) {
    alert(resp);
}

function showResponse2(resp) {
    alert(resp);
}

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