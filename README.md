# Space Mission Control System

Sistema de backend para monitoramento e controle de nave espacial, baseado nas ideias do arquivo `aldrin.txt`.

## Funcionalidades Implementadas

### 1. Controle de Velocidade da Luz
- Limitação automática da velocidade para evitar paradoxos temporais.
- Verificação em tempo real se a velocidade excede a velocidade da luz.

### 2. Regulação de Gravidade
- Monitoramento e ajuste da gravidade artificial.
- Campo `gravityLevel` no modelo `ShipStatus`.

### 3. Sistema de Navegação Automática
- Detecção de sinais vitais da tripulação.
- Ativação automática em caso de emergência (desmaio).
- Campo `autoNavigationActive` no modelo `ShipStatus`.

### 4. Simulação de Dobra Espacial (Modelo de Alcubierre)
- Cálculos matemáticos para dobra espacial.
- Modelo `WarpSimulation` com coordenadas iniciais/finais, velocidade da bolha, energia necessária.
- Serviço `WarpSimulationService` para cálculos e simulações.

### 5. Monitoramento de Oxigênio e Gerador de Atmosfera
- Monitoramento em tempo real do nível de oxigênio e CO2.
- Controle do gerador de atmosfera.
- Serviço `AtmosphereService` para alertas e ajustes.

### 6. Gestão de Energia Pós-Dobra
- Priorização de sistemas críticos após dobra.
- Cálculos de recuperação de energia via painéis solares.
- Serviço `EnergyManagementService` para simulação de consumo.

### 7. IA e Droids para Tratamento de Falhas
- Detecção automática de falhas nos sistemas.
- Coordenação de droids para reparos.
- Serviço `AIService` para análise e tomada de decisão.

## Estrutura do Projeto

- **Model**: Entidades como `ShipStatus`, `User`, `Quarantine`, `WarpSimulation`.
- **Service**: Lógica de negócio para cada funcionalidade.
- **Controller**: Endpoints REST para interação com o sistema.
- **Repository**: Interfaces para acesso a dados.
- **Exception**: Tratamento global de exceções.

## Tecnologias Utilizadas

- Java 21 (com Virtual Threads para alta performance)
- Spring Boot
- MySQL e Redis (via Docker)
- Swagger para documentação da API
- Lombok para simplificação de código

## Como Executar

### 1. Configurar Bancos de Dados
Execute os comandos Docker para iniciar MySQL e Redis:

```bash
# MySQL com usuário aldrin e senha educacional01
docker run --name mysql_space -p 3306:3306 -e MYSQL_ROOT_PASSWORD=educacional01 -e MYSQL_DATABASE=space_mission -e MYSQL_USER=aldrin -e MYSQL_PASSWORD=educacional01 -d mysql:latest --default-authentication-plugin=mysql_native_password

# Redis com senha educacional01
docker run --name redis_space -p 6379:6379 -d redis:latest redis-server --appendonly yes --requirepass educacional01
```

**Credenciais:**
- **MySQL**: Host: `localhost:3306`, User: `aldrin`, Password: `educacional01`, Database: `space_mission`
- **Redis**: Host: `localhost:6379`, Password: `educacional01`

### 2. Executar a Aplicação
```bash
cd BackEnd
mvn spring-boot:run
```

### 3. Acessar a Documentação
- **Swagger UI**: `http://localhost:8080/swagger-ui/`
- **API Base**: `http://localhost:8080/api/`

## Endpoints Principais

- `GET /api/ship/status`: Obter status da nave
- `POST /api/ship/warp/simulate`: Simular dobra espacial
- `GET /api/ship/speed/check`: Verificar limite de velocidade
- `GET /api/ship/atmosphere/monitor`: Monitorar atmosfera
- `GET /api/ship/energy/prioritize`: Priorizar energia
- `GET /api/ship/ai/detect-failures`: Detecção de falhas por IA

## Próximos Passos

- Implementar autenticação e autorização.
- Adicionar testes unitários e de integração.
- Melhorar os cálculos matemáticos com bibliotecas especializadas.
- Integrar com sensores reais da nave.# SpatialWarpSystem
