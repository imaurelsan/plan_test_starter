<?php

require_once __DIR__ . '/src/BookService.php';

$service = new BookService();

$uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
$method = $_SERVER['REQUEST_METHOD'];

header('Content-Type: application/json');

// GET /books
if ($uri === '/books' && $method === 'GET') {
    echo json_encode($service->getAllBooks());
    exit;
}

// POST /reserve/{id}
if (preg_match('/\/reserve\/(\d+)/', $uri, $matches) && $method === 'POST') {
    $bookId = (int)$matches[1];

    try {
        $result = $service->reserve($bookId);
        echo json_encode($result);
    } catch (Exception $e) {
        http_response_code(403);
        echo json_encode(['error' => $e->getMessage()]);
    }

    exit;
}

http_response_code(404);
echo json_encode(['error' => 'Not found']);