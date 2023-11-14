<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Submit a New Quote</title>
    <script>
        /* for whatever reason, this hits the servlet as /addTree.js when using
           src="addTree.js" not sure why */
        var tableCount = 1; // Keeps track of the number of tables

        function addNewTable() {
            // Clone the table
            var originalTable = document.getElementById("tree1");
            var br = document.getElementById("br-uh");
            var cloneTable = originalTable.cloneNode(true);
            var cloneBr = br.cloneNode(true);

            // Update the id of the cloned table
            cloneTable.id = "tree" + (++tableCount);

            // Update the names and ids of elements in the cloned table
            var elements = cloneTable.getElementsByTagName("*");
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].id) {
                    elements[i].id = elements[i].id.replace("1", tableCount);
                }
                if (elements[i].name) {
                    elements[i].name = elements[i].name.replace("1", tableCount);
                }
            }
            // cloneBr.id+="h";

            // Append the cloned table to the form
            document.getElementById("trees").appendChild(cloneTable);
            document.getElementById("trees").appendChild(cloneBr);
        }
    </script>
</head>
<body>
    <div align="center">
        <h1>New Quote:</h1>
        <form method="post" action="submit-quote" id="form" enctype="multipart/form-data">
            <table border="1" cellpadding="5">
                <thead>Time Details:</thead>
                <tr>
                    <th>What time would you like to start? </th>
                    <td>
                        <input type="datetime-local" name="startTime" required>
                    </td>
                </tr>
                <tr>
                    <th>What time would you like to end service? </th>
                    <td>
                        <input type="datetime-local" name="endTime" required>
                    </td>
                </tr>
                <tr>
                    <th>Notes: </th>
                    <td>
                        <textarea rows="5" cols="25" name="note"></textarea>
                    </td>
                </tr>
            </table>
            <br>
            <div id="trees">
                <table border="1" cellpadding="5" id="tree1">
                    <tr>
                        <th>Select size of tree:</th>
                        <td>
                            <select name="size_tree1" id="size_tree1" required>
                                <option value="Small">Small</option>
                                <option value="Medium">Medium</option>
                                <option value="Large">Large</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Select height of tree:</th>
                        <td>
                            <select name="height_tree1" id="height_tree1" required>
                                <option value="Short">Short ( < 20ft)</option>
                                <option value="Tall">Tall ( >= 20ft)</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Is the tree near your house?</th>
                        <td>
                            <select name="near_house1" id="near_house1" required>
                                <option value="Yes">Yes</option>
                                <option value="no">No</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Where's the location of the tree?</th>
                        <td>
                            (Ex: Front yard, Back yard, Garden, ect.) <br>
                            <input type="text" name = "location1"/>
                        </td>
                    </tr>
                    <tr>
                        <th>
                            Please upload 3<br>
                            pictures of your tree
                        </th>
                        <td>
                            <input type="file" name="tree1pic1" accept="image/png, image/jpeg" required>
                            <br>
                            <br>
                            <input type="file" name="tree1pic2" accept="image/png, image/jpeg" required>
                            <br>
                            <br>
                            <input type="file" name="tree1pic3" accept="image/png, image/jpeg" required>
                        </td>
                    </tr>
                </table>
                <br id="br-uh">
            </div>
            <button type="button" id="addTree" onclick="addNewTable()"> [+] Tree </button>
            <br>
            <input type="submit">
        </form>
    </div>
</body>
</html>
