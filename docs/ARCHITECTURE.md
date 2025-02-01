# Payment Hub Architecture

## Current Architecture

```mermaid
flowchart TB
    subgraph "Docker Environment"
        subgraph "Payment Hub Container"
            subgraph "API Layer"
                PC[PaymentMessageController]
            end

            subgraph "Monitoring Layer"
                ACT[Actuator Endpoints]
                MTR[Metrics Registry]
                HI[Health Indicators]
            end

            subgraph "Service Layer"
                PS[PaymentMessageService]
            end

            subgraph "Repository Layer"
                PR[PaymentMessageRepository]
            end

            subgraph "Data Layer"
                DB[(H2 Database)]
            end

            subgraph "Domain Layer"
                PM[PaymentMessage]
            end
        end
    end

    subgraph "Monitoring Endpoints"
        HE[/Health/]
        ME[/Metrics/]
        LE[/Loggers/]
        IE[/Info/]
    end

    Client --> PC
    PC --> PS
    PS --> PR
    PR --> DB
    PM --> PC
    PM --> PS
    PM --> PR

    PS -- "records metrics" --> MTR
    PS -- "reports health" --> HI
    
    ACT --> HE
    ACT --> ME
    ACT --> LE
    ACT --> IE
    
    MTR --> ACT
    HI --> ACT

    classDef container fill:#e7ecf3,stroke:#333,stroke-width:2px
    classDef component fill:#90EE90,stroke:#333,stroke-width:2px
    classDef endpoint fill:#FFE4B5,stroke:#333,stroke-width:2px
    classDef monitoring fill:#ADD8E6,stroke:#333,stroke-width:2px

    class PC,PS,PR,DB,PM component
    class HE,ME,LE,IE endpoint
    class ACT,MTR,HI monitoring