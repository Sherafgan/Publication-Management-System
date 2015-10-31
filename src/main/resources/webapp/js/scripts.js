// Empty JS for your own code to be here

function search() {
    var entity = document.getElementById("entity");
    var selectedEntity = entity.options[entity.selectedIndex].value;
    var attribute = document.getElementById("attribute");
    var selectedAttribute = attribute.options[attribute.selectedIndex].value;
    var insertedText = document.getElementById("in");
    var div = document.getElementById("images");
    var input = document.getElementById("in");
}

function login_by_email() {
    var email = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    if (validation()) // Calling validation function
    {
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
        alert(selectedEntity);
        $.ajax({
            type: "POST",
            url: "alive",
            //dataType: 'json',
            data: {attribute: selectedAttribute, value: searchText},
            dataType: text
        })
            .done(function (msg) {
                showResponse1(msg);
            })
            .fail(function (xhr, status, errorThrown) {
                alert("Sorry. Server unavailable. ");
            });
    }
    else if (selectedEntity == 2) {
        alert(selectedEntity);
        $.ajax({
            type: "POST",
            url: "alive",
            //dataType: 'json',
            data: {attribute: selectedAttribute, value: searchText},
            dataType: text
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

//selection dropdown's scripts

function configureDropDownListsForAddForm(ddl1) {
    var authorsAttributes = ['1'];
    var publicationAttributes = ['2', '3', '4', '5', '6', '7'];

    var authorPlaceHolders = ['Name'];
    var publicationPlaceHolders = ['Title', 'Year', 'Journal', 'Month', 'Publisher', 'ISBN'];

    switch (ddl1.value) {
        case '1':
            for (i = 0; i < authorsAttributes.length; i++) {
                makeInputFieldVisible(authorsAttributes[i], authorPlaceHolders[i]);
            }
            for (i = 0; i < publicationAttributes.length; i++) {
                makeInputFieldHidden(publicationAttributes[i]);
            }
            break;
        case '2':
            for (i = 0; i < publicationAttributes.length; i++) {
                makeInputFieldVisible(publicationAttributes[i], publicationPlaceHolders[i]);
            }
            for (i = 0; i < authorsAttributes.length; i++) {
                makeInputFieldHidden(authorsAttributes[i]);
            }
            break;
        default:
            //ddl2.options.length = 0;
            break;
    }

}

function makeInputFieldVisible(text, placeHolderText) {
    var input = document.getElementById(text);
    input.type = "text";
    input.placeholder = placeHolderText;
}

function makeInputFieldHidden(text) {
    var input = document.getElementById(text);
    input.type = "hidden";
}

function configureDropDownLists(ddl1, ddl2) {
    var authorsAttributes = ['Name'];
    var publicationAttributes = ['Title', 'Year', 'Journal', 'Month', 'Publisher', 'ISBN'];

    switch (ddl1.value) {
        case '1':
            ddl2.options.length = 0;
            for (i = 0; i < authorsAttributes.length; i++) {
                createInputField(ddl2, authorsAttributes[i], authorsAttributes[i]);
            }
            break;
        case '2':
            ddl2.options.length = 0;
            for (i = 0; i < publicationAttributes.length; i++) {
                createInputField(ddl2, publicationAttributes[i], publicationAttributes[i]);
            }
            break;
        default:
            ddl2.options.length = 0;
            break;
    }

}

function createInputField(ddl, text, value) {
    var opt = document.createElement('option');
    opt.value = value;
    opt.text = text;
    ddl.options.add(opt);
}