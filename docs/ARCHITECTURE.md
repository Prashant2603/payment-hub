# Payment Hub Architecture

## Current Architecture

```mermaid
flowchart TB
    subgraph "Current Implementation"
        direction TB
        
        subgraph "API Layer"
            PC[PaymentMessageController]
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

    %% Current Flow
    Client-->PC
    PC-->PS
    PS-->PR
    PR-->DB
    PM-->PC
    PM-->PS
    PM-->PR

    classDef current fill:#90EE90,stroke:#333,stroke-width:2px
    classDef future fill:#FFE4B5,stroke:#333,stroke-width:2px,stroke-dasharray: 5 5
    
    class PC,PS,PR,DB,PM current