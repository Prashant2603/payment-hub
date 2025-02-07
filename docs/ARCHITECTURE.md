# Payment Hub Architecture

## Current Architecture

```mermaid
flowchart TB
    subgraph "Docker Environment"
        subgraph "Load Balancer"
            LB[NGINX]
        end

        subgraph "Application Containers"
            subgraph "Instance 1"
                subgraph "API Layer 1"
                    PC1[PaymentMessageController]
                end

                subgraph "Service Layer 1"
                    PS1[PaymentMessageService]
                end

                subgraph "Repository Layer 1"
                    PR1[PaymentMessageRepository]
                end

                subgraph "Monitoring 1"
                    ACT1[Actuator]
                    LOG1[Logback]
                end
            end

            subgraph "Instance 2"
                subgraph "API Layer 2"
                    PC2[PaymentMessageController]
                end

                subgraph "Service Layer 2"
                    PS2[PaymentMessageService]
                end

                subgraph "Repository Layer 2"
                    PR2[PaymentMessageRepository]
                end

                subgraph "Monitoring 2"
                    ACT2[Actuator]
                    LOG2[Logback]
                end
            end

            subgraph "Instance 3"
                subgraph "API Layer 3"
                    PC3[PaymentMessageController]
                end

                subgraph "Service Layer 3"
                    PS3[PaymentMessageService]
                end

                subgraph "Repository Layer 3"
                    PR3[PaymentMessageRepository]
                end

                subgraph "Monitoring 3"
                    ACT3[Actuator]
                    LOG3[Logback]
                end
            end
        end

        subgraph "Database"
            DB[(PostgreSQL)]
        end

        subgraph "Monitoring Stack"
            PROM[Prometheus]
            GRAF[Grafana]
        end

        subgraph "ELK Stack"
            LS[Logstash]
            ES[(Elasticsearch)]
            KB[Kibana Dashboard]
        end
    end

    %% Application Flow
    Client-->LB
    LB-->PC1
    LB-->PC2
    LB-->PC3
    
    PC1-->PS1-->PR1
    PC2-->PS2-->PR2
    PC3-->PS3-->PR3
    
    PR1-->DB
    PR2-->DB
    PR3-->DB

    %% Logging Flow
    PS1 & PC1 & PR1--"generates logs"-->LOG1
    PS2 & PC2 & PR2--"generates logs"-->LOG2
    PS3 & PC3 & PR3--"generates logs"-->LOG3
    
    LOG1--"sends logs"-->LS
    LOG2--"sends logs"-->LS
    LOG3--"sends logs"-->LS
    
    LS--"indexes"-->ES
    KB--"queries"-->ES

    %% Monitoring Flow
    ACT1--"scrape metrics"-->PROM
    ACT2--"scrape metrics"-->PROM
    ACT3--"scrape metrics"-->PROM
    PROM--"visualize"-->GRAF

    PS1 & PC1--"metrics"-->ACT1
    PS2 & PC2--"metrics"-->ACT2
    PS3 & PC3--"metrics"-->ACT3

    classDef container fill:#e7ecf3,stroke:#333,stroke-width:2px
    classDef component fill:#90EE90,stroke:#333,stroke-width:2px
    classDef monitoring fill:#FFE4B5,stroke:#333,stroke-width:2px
    classDef elk fill:#ADD8E6,stroke:#333,stroke-width:2px
    classDef prometheus fill:#FF7F50,stroke:#333,stroke-width:2px
    classDef lb fill:#FFA07A,stroke:#333,stroke-width:2px

    class PC1,PC2,PC3,PS1,PS2,PS3,PR1,PR2,PR3,DB component
    class ACT1,ACT2,ACT3,LOG1,LOG2,LOG3 monitoring
    class LS,ES,KB elk
    class PROM,GRAF prometheus
    class LB lb