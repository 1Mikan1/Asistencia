<?php
header("Content-Type: application/json");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $usuario = $_POST['usuario'];
    $password = $_POST['password'];

    $conn = new mysqli("localhost", "tu_usuario", "tu_contraseña", "tu_base_de_datos");

    if ($conn->connect_error) {
        echo json_encode(["status" => "error", "message" => "Conexión fallida"]);
        exit();
    }

    $stmt = $conn->prepare("SELECT * FROM usuarios WHERE usuario = ? AND password = ?");
    $stmt->bind_param("ss", $usuario, $password);
    $stmt->execute();
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        echo json_encode(["status" => "success", "message" => "Usuario válido"]);
    } else {
        echo json_encode(["status" => "error", "message" => "Usuario o contraseña incorrectos"]);
    }

    $stmt->close();
    $conn->close();
}
?>
