# Payment Hub Architecture

## Current Architecture

```mermaid
flowchart TB
    subgraph "Docker Environment"
        subgraph "Application Container"
            subgraph "API Layer"
                PC[PaymentMessageController]
            end

            subgraph "Service Layer"
                PS[PaymentMessageService]
            end

            subgraph "Repository Layer"
                PR[PaymentMessageRepository]
            end

            subgraph "Monitoring"
                ACT[Actuator]
                LOG[Logback]
            end
        end

        subgraph "Database"
            DB[(H2 Database)]
        end

        subgraph "ELK Stack"
            LS[Logstash]
            ES[(Elasticsearch)]
            KB[Kibana Dashboard]
        end
    end

    %% Application Flow
    Client-->PC
    PC-->PS
    PS-->PR
    PR-->DB

    %% Logging Flow
    PS--"generates logs"-->LOG
    PC--"generates logs"-->LOG
    PR--"generates logs"-->LOG
    LOG--"sends logs"-->LS
    LS--"indexes"-->ES
    KB--"queries"-->ES

    %% Monitoring Flow
    PS--"metrics"-->ACT
    PC--"metrics"-->ACT
    
    classDef container fill:#e7ecf3,stroke:#333,stroke-width:2px
    classDef component fill:#90EE90,stroke:#333,stroke-width:2px
    classDef monitoring fill:#FFE4B5,stroke:#333,stroke-width:2px
    classDef elk fill:#ADD8E6,stroke:#333,stroke-width:2px

    class PC,PS,PR,DB component
    class ACT,LOG monitoring
    class LS,ES,KB elk