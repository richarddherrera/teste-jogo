# Script para testar autenticação
Write-Host "=== Testando Autenticação ===" -ForegroundColor Cyan

# Teste 1: Registro
Write-Host "`n1. Testando Registro..." -ForegroundColor Yellow
$registerBody = @{
    nickname = "testeUser"
    nomeReal = "Usuario Teste"
    email = "teste@email.com"
    senha = "senha123"
    dataNascimento = "2000-01-15"
} | ConvertTo-Json

try {
    $registerResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/register" `
        -Method POST `
        -ContentType "application/json" `
        -Body $registerBody
    
    Write-Host "✓ Registro bem-sucedido!" -ForegroundColor Green
    $token = ($registerResponse.Content | ConvertFrom-Json).token
    Write-Host "Token recebido: $($token.Substring(0, 20))..." -ForegroundColor Gray
} catch {
    Write-Host "✗ Erro no registro: $($_.Exception.Message)" -ForegroundColor Red
}

# Teste 2: Login
Write-Host "`n2. Testando Login..." -ForegroundColor Yellow
$loginBody = @{
    nickname = "aspas"
    senha = "senha123"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" `
        -Method POST `
        -ContentType "application/json" `
        -Body $loginBody
    
    Write-Host "✓ Login bem-sucedido!" -ForegroundColor Green
    $loginData = $loginResponse.Content | ConvertFrom-Json
    Write-Host "Jogador: $($loginData.jogador.nickname) - ELO: $($loginData.jogador.elo)" -ForegroundColor Gray
} catch {
    Write-Host "✗ Erro no login: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Testes Concluídos ===" -ForegroundColor Cyan
