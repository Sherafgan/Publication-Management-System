// Empty JS for your own code to be here

function login_by_email() {
    if (validation()) // Calling validation function
    {
        load_DB();
        redirectToMainPage();
    }
}

function load_DB() {
    $.ajax({
            type: "GET",
            url: "load_db",
            dataType: 'json',
            data: {}

        })
        .done(function (msg) {
            $.jsontotable(msg, {id: '#jsontotable', header: false});
        })
        .fail(function () {
            alert("Sorry. Server unavailable. ");
        });
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

$('#btnOK').click(function () {
    var entity = document.getElementById("modalEntity");
    var selectedEntity = entity.options[entity.selectedIndex].value;
    if (selectedEntity == 1) {
        var nameInput = document.getElementById("1");
        var name = nameInput.value;
        var homepageURLInput = document.getElementById("2");
        var homepageURL = homepageURLInput.value;
        $.ajax({
                type: "GET",
                url: "survive",
                dataType: 'text',
                data: {entity: entity.value, name: name, homepageURL: homepageURL}
            })
            .done(function () {
                alert("Record added!");
            })
            .fail(function () {
                alert("Sorry. Data wasn't loaded ");
            });
    }
    else if (selectedEntity == 2) {
        var titleInput = document.getElementById("3");
        var title = titleInput.value;
        var yearInput = document.getElementById("4");
        var year = yearInput.value;
        var journalInput = document.getElementById("5");
        var journal = journalInput.value;
        var monthInput = document.getElementById("6");
        var month = monthInput.value;
        var publisherInput = document.getElementById("7");
        var publisher = publisherInput.value;
        var isbnInput = document.getElementById("8");
        var isbn = isbnInput.value;
        $.ajax({
                type: "GET",
                url: "survive",
                dataType: 'text',
                data: {
                    entity: entity.value,
                    title: title,
                    year: year,
                    journal: journal,
                    month: month,
                    publisher: publisher,
                    isbn: isbn
                }
            })
            .done(function () {
                alert("Record added!");
            })
            .fail(function () {
                alert("Sorry. Data wasn't loaded ");
            });
    }
});

$('#deleteOK').click(function () {
    var deletionEntity = document.getElementById("deletionEntity");
    var tupleID = document.getElementById("idEntry");
    $.ajax({
            type: "GET",
            url: "deleteRecord",
            dataType: 'text',
            data: {tupleID: tupleID.value, deletionEntity: deletionEntity.value}
        })
        .done(function () {
            alert("Record deleted!");
        })
        .fail(function () {
            alert("Sorry. Record wasn't found!");
        });

});

$('#updateOK').click(function () {
    var updateIDEntry = document.getElementById("updateIDEntry");

    var updateEntity = document.getElementById("updateEntity");

    if (updateEntity.value == 1) {
        var nameValue = document.getElementById("upd1");
        var homepageURLValue = document.getElementById("upd2");

        $.ajax({
                type: "GET",
                url: "updateRecord",
                dataType: 'text',
                data: {
                    updateIDEntry: updateIDEntry.value,
                    updateEntity: updateEntity.value,
                    nameValue: nameValue.value,
                    homepageURLValue: homepageURLValue.value
                }
            })
            .done(function () {
                alert("Record updated!");
            })
            .fail(function () {
                alert("Sorry. Record wasn't found in 'Author'-s!");
            });
    } else {
        var publicationDeterminer = document.getElementById("publicationDeterminer");

        var titleValue = document.getElementById("upd3");
        var yearValue = document.getElementById("upd4");

        if (publicationDeterminer.value == 1) {
            var journalValue = document.getElementById("upd5");
            var monthValue = document.getElementById("upd6");

            $.ajax({
                    type: "GET",
                    url: "updateRecord",
                    dataType: 'text',
                    data: {
                        updateIDEntry: updateIDEntry.value,
                        updateEntity: updateEntity.value,
                        publicationDeterminer: publicationDeterminer.value,
                        titleValue: titleValue.value,
                        yearValue: yearValue.value,
                        journalValue: journalValue.value,
                        monthValue: monthValue.value
                    }
                })
                .done(function () {
                    alert("Record updated!");
                })
                .fail(function () {
                    alert("Sorry. Record wasn't found!");
                });
        } else {
            var publisherValue = document.getElementById("upd7");
            var isbnValue = document.getElementById("upd8");

            $.ajax({
                    type: "GET",
                    url: "updateRecord",
                    dataType: 'json',
                    data: {
                        updateIDEntry: updateIDEntry.value,
                        updateEntity: updateEntity.value,
                        publicationDeterminer: publicationDeterminer.value,
                        titleValue: titleValue.value,
                        yearValue: yearValue.value,
                        publisherValue: publisherValue.value,
                        isbnValue: isbnValue.value
                    }
                })
                .done(function () {
                    alert("Record updated!");
                })
                .fail(function () {
                    alert("Sorry. Record wasn't found!");
                });
        }
    }
});


$("#submitbtn").click(function () {
    var entity = document.getElementById("entity");
    var selectedEntity = entity.options[entity.selectedIndex].value;
    var attribute = document.getElementById("attribute");
    var selectedAttribute = attribute.options[attribute.selectedIndex].value;
    var textEdit = document.getElementById("in");
    var searchText = textEdit.value;

    if (selectedEntity == 1 || selectedEntity == 2) {
        document.getElementById("jsontotable").innerHTML = "";
        $.ajax({
                type: "GET",
                url: "alive",
                dataType: 'json',
                data: {entity: selectedEntity, attribute: selectedAttribute, search: searchText}

            })
            .done(function (msg) {
                $.jsontotable(msg, {id: '#jsontotable', header: false});
            })
            .fail(function () {
                alert("Sorry. Server unavailable. ");
            });
    }
    else {
        $("p").text("Not valid!").show().fadeOut(1000);
        event.preventDefault();
    }
});

//function makeTable(container, data) {
//    var table = $("<table/>").addClass('CSSTableGenerator');
//    $.each(data, function (rowIndex, r) {
//        var row = $("<tr/>");
//        $.each(r, function (colIndex, c) {
//            row.append($("<t" + (rowIndex == 0 ? "h" : "d") + "/>").text(c));
//        });
//        table.append(row);
//    });
//    return container.append(table);
//}
//
//function appendTableColumn(table, rowData) {
//    var lastRow = $('<tr/>').appendTo(table.find('tbody:last'));
//    $.each(rowData, function (colIndex, c) {
//        lastRow.append($('<td/>').text(c));
//    });
//
//    return lastRow;
//}

//selection dropdown's scripts

function configureDropDownListsForAddForm(ddl1) {
    var authorsAttributes = ['1', '2'];
    var publicationAttributes = ['3', '4', '5', '6', '7', '8'];

    var authorPlaceHolders = ['Name', 'HomepageURL'];
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

function configureDropDownListsForUpdateForm(ddl1) {
    var authorsAttributes = ['upd1', 'upd2'];
    var publicationAttributes = ['upd3', 'upd4', 'upd5', 'upd6', 'upd7', 'upd8'];

    var articleAttributes = ['upd3', 'upd4', 'upd5', 'upd6'];

    var authorPlaceHolders = ['Name', 'HomepageURL'];

    var articlePlaceHolders = ['Title', 'Year', 'Journal', 'Month'];

    switch (ddl1.value) {
        case '1':
            document.getElementById("publicationDeterminer").style.opacity = 0;
            for (i = 0; i < authorsAttributes.length; i++) {
                makeInputFieldVisible(authorsAttributes[i], authorPlaceHolders[i]);
            }
            for (i = 0; i < publicationAttributes.length; i++) {
                makeInputFieldHidden(publicationAttributes[i]);
            }
            break;
        case '2':
            document.getElementById("publicationDeterminer").style.opacity = 1;
            for (i = 0; i < authorsAttributes.length; i++) {
                makeInputFieldHidden(authorsAttributes[i]);
            }
            for (i = 0; i < articleAttributes.length; i++) {
                makeInputFieldVisible(articleAttributes[i], articlePlaceHolders[i]);
            }
            break;
        default:
            //ddl2.options.length = 0;
            break;
    }

}

function articleOrBookFields(ddl) {
    var articleAttributes = ['upd3', 'upd4', 'upd5', 'upd6'];
    var bookPlaceAttributes = ['upd3', 'upd4', 'upd7', 'upd8'];

    var articlePlaceHolders = ['Title', 'Year', 'Journal', 'Month'];
    var bookPlaceHolders = ['Title', 'Year', 'Publisher', 'ISBN'];
    switch (ddl.value) {
        case '1':
            for (i = 0; i < bookPlaceAttributes.length; i++) {
                makeInputFieldHidden(bookPlaceAttributes[i]);
            }
            for (i = 0; i < articleAttributes.length; i++) {
                makeInputFieldVisible(articleAttributes[i], articlePlaceHolders[i]);
            }
            break;
        case '2':
            for (i = 0; i < articleAttributes.length; i++) {
                makeInputFieldHidden(articleAttributes[i]);
            }
            for (i = 0; i < bookPlaceAttributes.length; i++) {
                makeInputFieldVisible(bookPlaceAttributes[i], bookPlaceHolders[i]);
            }
            break;
        default:
            //ddl2.options.length = 0;
            break;
    }
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