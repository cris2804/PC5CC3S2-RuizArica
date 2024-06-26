# Práctica Calificada 5 CC3S2

### Descripción del proyecto

El juego Tower Defense es un videojuego de consola donde el jugador debe defender su base de oleadas de enemigos colocando torres en lugares estratégicos del mapa. El proyecto incluirá el uso
de mocks, stubs y fakes para pruebas unitarias y de integración utilizando Mockito y pruebas de mutación.
Objetivos del ejercicio:

1. Configurar y ejecutar contenedores Docker.
2. Configurar redes y volúmenes en Docker.
3. Usardocker exec para interactuar con contenedores enejecución.
4. Implementar aplicaciones con Docker Compose.
5. Desplegar aplicaciones en Kubernetes.
6. Realizar pruebas unitarias y de integración utilizando Mockito.
7. Implementar pruebas de mutación para verificar la calidad de las pruebas.

## Codigo en java para el juego

**1. Clase PrincipalTowerDefenseGame**

```java
package org.example;
import java.util.*;
public class TowerDefenseGame {
    private Map map;
    private Player player;
    private List<Wave> waves;
    public TowerDefenseGame() {
        this.map = new Map();
        this.player = new Player();
        this.waves = new ArrayList<>();
    }
    public void placeTower(Tower tower, int x, int y) {
        map.placeTower(tower, x, y);
    }
    public void startWave() {
        Wave wave = new Wave();
        waves.add(wave);
        wave.start();
    }
    public void gameState() {
        System.out.println(map);
        System.out.println("Puntuación: " + player.getScore());
        System.out.println("Vida de la base: " + player.getBaseHealth());
    }
    public static void main(String[] args) {
        TowerDefenseGame game = new TowerDefenseGame();
        game.placeTower(new Tower('T'), 2, 1);
        game.startWave();
        game.gameState();
    }
}
```

**2. ClaseMap**

```java
package org.example;

import java.util.List;

public class Map {
    private char[][] grid;
    public Map() {
        grid = new char[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                grid[i][j] = ' ';
            }
        }
    }
    public void placeTower(Tower tower, int x, int y) {
        grid[x][y] = tower.getSymbol();
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row) {
                sb.append("[").append(cell).append("]");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
```

**3. ClasePlayer**

```java
package org.example;

public class Player {
    private int score;
    private int baseHealth;
    public Player() {
        this.score = 0;
        this.baseHealth = 100;
    }
    public int getScore() {
        return score;
    }
    public int getBaseHealth() {
        return baseHealth;
    }
}
```

**4. ClaseTower**

```java
public class Wave {
 public void start() {
 System.out.println("Oleada iniciada!");
 }
}
```

## Ejercicio 1: Configuración y uso de docker (3 puntos)

**Teoría:**
• Describe los principios fundamentales de los contenedores Docker y su arquitectura interna.

Docker es una plataforma **open source** , sirve para enviar y ejecutar aplicaciones. Permite mover nuestras aplicaciones desde un ambiente de desarrollo a pruebas  para finalmente mandarlo a produccion .Presente 3 características principales ligereza, autonomía y escalabilidad. Con respecto a su **arquitectura interna (**cliente-servidor) contiene lo siguiente: 

- Docker Cliente : Es la principal manera en la que el usuario de docker interactua con la plataforma Docker .
- Docker Host : docker daemon, images y containes
- Docker registry : Guardan a las imagnes, extensiones y plugins .Es decir son el componente de distribución de Docker.

Explica cómo Docker maneja la seguridad y el aislamiento de contenedores.
• Compara y contrasta Docker con soluciones de virtualización tradicionales, como VMware y VirtualBox. Discute las ventajas y desventajas de cada enfoque.

Mientras docker es mejor cuando se rquiere eficincnia , rapidez  y portabilidad sirve para entorno de desarrrllo y des pliegue continui(CI/CD) ,las maquinas virtuales se usan cuando se requieresn un alto nivel de aislamiento 

**Práctico:**
• Escribe un Dockerfile para la aplicación Tower Defense que incluya la instalación de todas las dependencias necesarias. Asegúrate de optimizar el Dockerfile para reducir el tamaño de la
imagen final.

En base a las especificaciones de mi proyecto : 

```java
#version de java 
FROM openjdk:17 

WORKDIR /app

COPY . .
WORKDIR /app/src/main/java

RUN javac org/example/*.java

WORKDIR /app/src/main/java

CMD ["sh", "-c", "java -cp /app/src/main/java org.example.TowerDefenseGame && tail -f /dev/null"]

```

• Construye y ejecuta el contenedor Docker utilizando el Dockerfile creado. Utiliza `docker exec` para acceder al contenedor y verificar que la aplicación funcione correctamente.

Abrimos el terminal y nos dirijimos al directorio donde se encuentra nuestro proyecto `TowerDefenseGame` 

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled.png)

Escribimos el siguiente comando para comando para acceder al contenedor 

`docker exec -it tower-defense-container /bin/bash` 

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%201.png)

• Configura una red personalizada para la aplicación Tower Defense. Implementa múltiples
contenedores que interactúen entre sí a través de esta red personalizada.

`docker network create game-network`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%202.png)

## Ejercicio 2: Redes y volúmenes en Docker (3 puntos)

**Teoría:**

• Explica en detalle cómo Docker maneja las redes y los volúmenes. Discute los diferentes
tipos de redes (bridge, host, overlay) y cuándo es apropiado usar cada una.

• Describe los mecanismos de persistencia de datos en Docker, incluyendo volúmenes y bind
mounts. Explica las diferencias entre ellos y las mejores prácticas para su uso.

**Práctico**:
• Crea una red personalizada para el proyecto Tower Defense y configura los contenedores
para que utilicen esta red.

`docker network create game-network`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%202.png)

• Implementa un volumen Docker para almacenar los datos del juego de forma persistente.
Asegúrate de que el volumen se monte correctamente y que los datos persistan después de
reiniciar el contenedor.

`docker volume create game-data`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%203.png)

• Utiliza docker-compose para definir los servicios de la aplicación Tower Defense, incluyendo
redes y volúmenes. Escribe un archivo docker-compose.yml que configure estos servicios y
despliega la aplicación utilizando Docker Compose.

Creamos el archivo docker-compose.yml

```java
version: '3'
services:
  game:
    image: tower-defense-game
    networks:
      - game-network
    volumes:
      - game-data:/app/data

networks:
  game-network:
    driver: bridge

volumes:
  game-data:
    driver: local

```

Ahora lo desplegamos con `docker-compose up -d` :

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%204.png)

## Ejercicio 3: Orquestación con Kubernetes (4 puntos)

**Teoría:**
• Describe la arquitectura de Kubernetes y sus componentes principales, incluyendo el API server, etcd, scheduler, y kubelet. Explica cómo estos componentes interactúan para gestionar un clúster de Kubernetes.

La arquitectura de kubernets se basa en el panel de control , los nodos y los controladores.
• Discute las estrategias de escalabilidad y alta disponibilidad en Kubernetes. Explica cómo Kubernetes maneja la recuperación de fallos y la gestión de réplicas.

Kubernetes proporciona un entorno altamente escalable y  disponible mediante el uso de estrategias como el escalado horizontal, el escalado automático, el reinicio automático de pods, la tolerancia a fallos y la gestión de réplicas a través de controladores de replicación.

- **Recuperación de fallos**:  Si un pod falla, Kubernetes intentará reiniciarlo automáticamente. Si un nodo falla, los pods que estaban en ese nodo se reprogramarán en otros nodos disponibles.
- **Gestión de réplicas**: Si el número de réplicas cae por debajo del valor deseado, Kubernetes creará nuevos pods para restaurar el estado deseado.

**Práctico:**
• Escribe un archivo deployment.yaml para la aplicación Tower Defense. Asegúrate de definir
los recursos necesarios (CPU, memoria) y las políticas de escalabilidad.

Creamos el archivo deployment.yaml : 

```java
apiVersion: apps/v1
kind: Deployment
metadata:
  name: tower-defense-deployment
  labels:
    app: tower-defense
spec:
  replicas: 3
  selector:
    matchLabels:
      app: tower-defense
  template:
    metadata:
      labels:
        app: tower-defense
    spec:
      containers:
      - name: tower-defense
        image: tower-defense-game:latest
        ports:
        - containerPort: 8080
```

• Implementa un Service en Kubernetes para exponer la aplicación Tower Defense a través de
una IP pública. Utiliza un LoadBalancer para distribuir el tráfico entre múltiples réplicas de la
aplicación.

Creamos un service.yaml  : 

```java
apiVersion: v1
kind: Service
metadata:
  name: tower-defense-service
spec:
  selector:
    app: tower-defense
  ports:
    - protocol: TCP
      port: 80
      targetPort: 8080
  type: LoadBalancer

```

• Despliega la aplicación Tower Defense en un clúster de Kubernetes. Utiliza kubectl para
gestionar el despliegue y verificar que la aplicación funcione correctamente en el clúster.

Primero inicializamos Kubernet:

`minikube start`  

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%205.png)

Aplicamos los archivos de configuración en Kubernetes
`kubectl apply -f deployment.yaml`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%206.png)

`kubectl apply -f service.yaml`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%207.png)

Verificamos el estado del despliegue
`kubectl get pods`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%208.png)

`kubectl get services`

![Untitled](Pra%CC%81ctica%20Calificada%205%20CC3S2%2013a0636175594bc69fb827b9064e0dc3/Untitled%209.png)

## Ejercicio 4: Pruebas unitarias y de integración con Mockito (4 puntos)

**Teoría:**
• Explica los conceptos de mocks, stubs y fakes. Discute cuándo y cómo se deben utilizar estos patrones en las pruebas unitarias.

Mocks son objetos falsos con un comportamiento predefinido  y expectativas.

Los stubs brindan respuestas a las llamadas realizadas durante la prueba y, por lo general, no responden a nada fuera de lo programado para la prueba.

Los fakes son implementaciones simplificadas de componentes que tienen funcionalidades limitadas diseñadas solo para hacer viables las pruebas.

Se debe utilizar cuando se rquiera asislar componentes especifico del proyecto , es por ellos que las pruebas no depende de la infraestructra externa, lo que reduce la complejidad

• Describe el proceso de creación de pruebas unitarias con Mockito. Explica cómo se pueden simular dependencias y verificar comportamientos en las pruebas.

**Práctico:**
• Escribe pruebas unitarias para la clase TowerDefenseGame utilizando Mockito para simular las dependencias de Map, Player y Wave.

```java
package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

public class TowerDefenseGameTest {
    private TowerDefenseGame game;
    private Map map;
    private Player player;
    private Wave wave;

    @BeforeEach
    public void setUp() {
        map = Mockito.mock(Map.class);
        player = Mockito.mock(Player.class);
        wave = Mockito.mock(Wave.class);
        game = new TowerDefenseGame();
    }

    @Test
    public void testStartGame() {
        wave.start();
        game.startWave();
        verify(wave).start();
    }
}
```

• Implementa pruebas de integración que verifiquen la interacción entre las clases principales (TowerDefenseGame, Map, Player, Wave). Utiliza Mockito para controlar y verificar el comportamiento de las dependencias en estas pruebas.

```java
package org.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
public class TowerDefenseGameTest {
    @Mock
    private Map mockMap;
    @Mock
    private Player mockPlayer;
    @InjectMocks
    private TowerDefenseGame game;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testPlaceTower() {
        Tower mockTower = mock(Tower.class);
        game.placeTower(mockTower, 2, 2);
        verify(mockMap).placeTower(mockTower, 2, 2);
    }
}
```

• Configura un pipeline de integración continua (CI) que ejecute automáticamente las pruebas unitarias e informe sobre los resultados. Utiliza herramientas como Jenkins o GitHub Actions
para implementar este pipeline (opcional).

## Ejercicio 5: Pruebas de mutación (4 puntos)

### **Teoría:**

• Define qué son las pruebas de mutación y cómo contribuyen a la mejora de la calidad del software. Explica los tipos de operadores de mutación y su propósito.

Las pruebas de mutación,  son una técnica de pruebas de caja blanca que ayuda  a desarrollar una serie de nuevas comprobaciones de software al tiempo que verifican los procesos actuales de un proyecto. 

Los tipos de operadores de mutación de mutacion aritmetica ,relacional ,logica,condiciones , variables. Tienen como propósito simular errores posibles que podrían ocurrir en el codigo del programa.

• Discute las métricas utilizadas para evaluar la efectividad de las pruebas de mutación, como la tasa de mutación (mutation score) y la cobertura de mutación.

### **Práctico:**

• Configura una herramienta de pruebas de mutación, como PIT, en el proyecto Tower Defense. Asegúrate de integrar la herramienta en el pipeline de CI (opcional).

• Implementa pruebas de mutación para la clase Map y analiza los resultados. Asegúrate de identificar y corregir las pruebas unitarias que no detecten mutaciones.

• Realiza un informe detallado sobre la calidad de las pruebas del proyecto Tower Defense, basado en los resultados de las pruebas de mutación. Incluye recomendaciones para mejorar la cobertura y efectividad de las pruebas.

## Ejercicio 6: Diseño por contrato (Design by Contract) (2 puntos)

### **Teoría:**

• Explica el concepto de diseño por contrato y cómo se aplica en el desarrollo de software.

Es una metodología de desarrollo de software que en la que los componentes de softwre deben funcionar bajo un contrato . el cual define sus obligaciones y beneficios.El contrato se compone de precondiciones, invariantes y postcondiciones.

•Discute las diferencias entre precondiciones, postcondiciones e invariantes.

Las precondiciones aseguran que el método se ejecute con entradas válidas y en un estado válido.Mientras que las postcondiciones verifican que el método ha producido el resultado esperado y ha dejado el sistema en un estado válido.Por el contrario las invariantes aseguran la consistencia y validez del estado interno del objeto.

• Describe cómo el diseño por contrato puede mejorar la robustez y mantenibilidad del código.

**Mejora la robustez** al verificar las precondiciones antes de ejecutar un método ya que detecta entradas invalidas de forma inmediata. Por ejemplos si un método requiere un índice dentro de los limites de una lista , con  la precondición se verificara esto y lanzar una excepción en caso el índice sea invalido. Él diseño por contrato tambien mejora la **mantenibilidad de codigo** al tener una documentación clara ,al utilizar aserciones durante el testing.

### **Práctico:**

• Aplica el diseño por contrato a la clase Tower. Define las precondiciones, postcondiciones e invariantes de los métodos principales de la clase.

• Escribe pruebas unitarias que verifiquen el cumplimiento de los contratos definidos para la clase Tower. Utiliza herramientas como Java Assertions para implementar estas verificaciones.

• Realiza una revisión de código para asegurarte de que todas las clases del proyecto Tower Defense siguen los principios del diseño por contrato. Documenta cualquier ajuste o mejora necesaria en el código.

[ACTIVIDAD : **Docker-kubernetes-Microservicios(24ABRIL)**](https://www.notion.so/ACTIVIDAD-Docker-kubernetes-Microservicios-24ABRIL-3a7e78bd8ad14b528797359584c36a9e?pvs=21)