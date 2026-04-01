<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Feedback Prototype</title>
</head>
<body>
<h1>Feedback System Prototype</h1>

<h2>Opprett forelesning</h2>
<form action="createLecture" method="post">
    <label>Navn:</label><br>
    <input type="text" name="navn" required><br><br>

    <label>Tidspunkt:</label><br>
    <input type="datetime-local" name="tidspunkt"><br><br>

    <label>Sted:</label><br>
    <input type="text" name="sted"><br><br>

    <button type="submit">Lagre forelesning</button>
</form>

<hr>

<h2>Gi tilbakemelding</h2>
<form action="submitFeedback" method="post">
    <label>Forelesning ID:</label><br>
    <input type="number" name="forelesningId" required><br><br>

    <label>Student ID:</label><br>
    <input type="text" name="studentId" required><br><br>

    <label>Farge:</label><br>
    <select name="farge" required>
        <option value="gronn">Grønn</option>
        <option value="gul">Gul</option>
        <option value="rod">Rød</option>
    </select><br><br>

    <button type="submit">Send tilbakemelding</button>
</form>

<hr>

<h2>Se resultater</h2>
<form action="viewResults" method="get">
    <label>Forelesning ID:</label><br>
    <input type="number" name="forelesningId" required><br><br>

    <button type="submit">Vis resultater</button>
</form>

</body>
</html>