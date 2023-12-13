<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <title>Submit a New Quote</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        var tableCount = 1;

        function addNewTable() {
            var originalTable = document.getElementById("tree1");
            var cloneTable = originalTable.cloneNode(true);

            cloneTable.id = "tree" + (++tableCount);

            var elements = cloneTable.getElementsByTagName("*");
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].id) {
                    elements[i].id = elements[i].id.replace("1", tableCount);
                }
                if (elements[i].name) {
                    elements[i].name = elements[i].name.replace("1", tableCount);
                }
            }

            document.getElementById("trees").appendChild(cloneTable);
        }
    </script>
</head>
<body class="bg-indigo-300">
<div class="flex justify-center items-center min-h-screen">
    <div class="bg-white shadow-lg p-6 max-w-4xl w-full">
        <h1 class="text-xl font-bold text-center mb-4">New Quote:</h1>
        <form method="post" action="submit-quote" id="form" class="space-y-4" enctype="multipart/form-data">
            <div class="space-y-2">
                <div>
                    <label class="block text-gray-700 font-bold mb-2">Start Time:</label>
                    <input type="datetime-local" name="startTime"
                           class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           required>
                </div>
                <div>
                    <label class="block text-gray-700 font-bold mb-2">End Time:</label>
                    <input type="datetime-local" name="endTime"
                           class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                           required>
                </div>
                <div>
                    <label class="block text-gray-700 font-bold mb-2">Notes:</label>
                    <textarea rows="5" name="note"
                              class="shadow border  w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"></textarea>
                </div>
            </div>

            <div id="trees" class="space-y-4">
                <div id="tree1" class="border p-4  shadow">
                    <table class="w-full text-left">
                        <tr>
                            <th class="py-2">Select size of tree:</th>
                            <td>
                                <select name="size_tree1" id="size_tree1" required
                                        class="shadow border  py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                    <option value="Small">Small</option>
                                    <option value="Medium">Medium</option>
                                    <option value="Large">Large</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>Select height of tree:</th>
                            <td>
                                <input type="number" name="height_tree1" id="height_tree1" required
                                       class="shadow border  py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </td>
                        </tr>
                        <tr>
                            <th>Is the tree near your house?</th>
                            <td>
                                <select name="near_house1" id="near_house1" required
                                        class="shadow border  py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                                    <option value="Yes">Yes</option>
                                    <option value="No">No</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th>Location of the tree:</th>
                            <td>
                                <input type="text" name="location1" required
                                       class="shadow border  py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline">
                            </td>
                        </tr>
                        <tr>
                            <th>Upload pictures of your tree:</th>
                            <td>
                                <input type="file" name="tree1pic1" accept="image/png, image/jpeg" required
                                       class="py-2">
                                <br>
                                <input type="file" name="tree1pic2" accept="image/png, image/jpeg" required
                                       class="py-2">
                                <br>
                                <input type="file" name="tree1pic3" accept="image/png, image/jpeg" required
                                       class="py-2">
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <button type="button" id="addTree" onclick="addNewTable()"
                    class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4  focus:outline-none focus:shadow-outline my-2">
                [+] Add Another Tree
            </button>

            <div class="mt-4">
                <input type="submit" value="Submit Quote"
                       class="bg-green-500 hover:bg-green-600 text-white font-bold py-2 px-4  focus:outline-none focus:shadow-outline w-full">
            </div>
        </form>
    </div>
</div>
</body>
</html>
