BRT-DAI2024 — BRT Braga (Bus Rapid Transport) Desktop App

- University project for the city of Braga related to the BRT Braga system (Bus Rapid Transport) — a desktop application focused on bus ticketing, routes, and schedules.
- Implemented in Java 17 with Swing (NetBeans GUI Builder) and Ant. Uses MySQL for persistence and ZXing for QR Code generation.

Overview
- Main menu and navigation between views (buy tickets, my tickets, lines/schedules).
- Buy Single (per line) or Custom (route between stops) tickets.
- Shortest path calculation using Dijkstra, with price estimated from the number of line changes.
- Generate QR Codes for purchased tickets (summary for Single tickets or per Custom route).
- Browse line schedules by Line/Direction with a results table.
- In‑app toast notifications (success/warning/info).

Tech Stack
- Java 17, Swing, Ant (NetBeans project)
- MySQL (Connector/J)
- ZXing (QR Code) and TimingFramework (animations)
- Lombok (getters/setters/boilerplate)
- Local JARs under `src/Assets/Libraries` are referenced by Ant (`nbproject/project.properties`).

Project Structure
- `src/Menus` — Swing JFrames and UI (NetBeans GUI). Examples: `MenuPrincipal`, `MenuBRT`, `MenuComprarBilhetes`, `MenuBilhetes`, `MenuBilhetePersonalizado`, `MenuLinhas`.
- `src/Main` — Core logic: `Main.java`, Dijkstra/Node (routing), notification components.
- `src/Assets` — Images, libraries (`Libraries/`), and sample data (`BaseDados/*.txt`).
- `nbproject` — NetBeans/Ant configuration. `main.class=Main.Main`.
- `build.xml` — Main Ant script (imports `nbproject/build-impl.xml`).

Prerequisites
- Java JDK 17 on PATH
- Ant (or NetBeans 12+ with Ant)
- MySQL 8.x (local or remote)

Build & Run (Ant/CLI)
- Compile: `ant clean compile`
- Build JAR: `ant clean jar`
- Run: `ant run`
- Run the JAR: `java -jar dist/BRT-DAI2024.jar`

Note: The project already references the JARs from `src/Assets/Libraries`; Ant resolves the classpath automatically (see `nbproject/project.properties`).

Build & Run (NetBeans)
- Open the project folder in NetBeans
- Ensure JDK 17 is the selected platform
- Run (F6) — `main.class` points to `Main.Main`

Database Configuration
- The app connects to MySQL in `src/Menus/MenuBRT.java`. Update the URL/credentials for your environment.
  - Recommended JDBC URL (UTF‑8 + timezone):
    `jdbc:mysql://localhost:3306/brt_dai?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC`
  - Driver class: `com.mysql.cj.jdbc.Driver`

Suggested Schema (DDL)
```sql
CREATE TABLE TipoBilhetes (
  Linha  VARCHAR(50) PRIMARY KEY,
  Preco  DECIMAL(5,2) NOT NULL
);

CREATE TABLE BilhetesUnicos (
  Linha               VARCHAR(50) PRIMARY KEY,
  Quantidade_Bilhetes INT NOT NULL
);

CREATE TABLE BilhetesPersonalizados (
  ID         INT AUTO_INCREMENT PRIMARY KEY,
  ID_Cliente INT NOT NULL,
  Rota       TEXT NOT NULL
);

CREATE TABLE Horarios (
  Linha   VARCHAR(50) NOT NULL,
  Direcao VARCHAR(120) NOT NULL,
  Horario TIME NOT NULL,
  Estacao VARCHAR(120) NOT NULL
);

-- Graph edges used for routing
CREATE TABLE Paragens (
  Nome     VARCHAR(120) NOT NULL,
  Proximo  VARCHAR(120) NOT NULL,
  Distancia INT NOT NULL,           -- edge weight (e.g., meters)
  Linha    VARCHAR(120) NOT NULL    -- one or more lines, comma‑separated
);
```

Sample Data
- Reference files under `src/Assets/BaseDados` can help seed the DB:
  - Schedules: `src/Assets/BaseDados/HorarioLinhas.txt` (columns: Linha,Direcao,Horario,Estacao)
  - Tickets (legacy example): `src/Assets/BaseDados/Bilhetes.txt`
- For quick testing, insert coherent entries for lines/schedules/stops, e.g.:
  - `TipoBilhetes`: (Amarela, 0.74), (Azul, 0.74), (Verde, 0.74), (Vermelha, 0.74)
  - `Paragens`: directed edges `Nome -> Proximo` with `Distancia` and the serving `Linha`(s)

Key Code Entry Points
- Entry point: `src/Main/Main.java`
- Database connection (adjust credentials): `src/Menus/MenuBRT.java`
- Dijkstra and graph model: `src/Main/Dijkstra/Dijkstra.java`, `src/Main/Dijkstra/Node.java`
- QR Code generation (Single): `src/Menus/MenuBilhetes.java`
- QR Code generation (Custom): `src/Menus/MenuBilhetePersonalizado.java`

Main Flows
- Buy Tickets
  - Single: choose Line and Quantity; price ≈ `TipoBilhetes.Preco * quantity`.
  - Custom: choose Start/End stops; route via Dijkstra over `Paragens`. Current price formula in code is proportional to line changes: `totalChanges * 0.74 - totalChanges * 0.05 * 0.74`.
- Tickets
  - Lists Single tickets from `BilhetesUnicos` and generates a summary QR.
- Custom Tickets
  - Iterates `BilhetesPersonalizados` and generates a QR per stored route.
- Lines
  - Filters `Horarios` by selected Line/Direction and shows a table.

Technical Notes
- Graph & Dijkstra
  - Generic `Dijkstra<T>` and `Node<T>`; each edge stores serving line(s) and distance. The algorithm prefers staying on the same line when possible to reduce changes.
- Notifications/UI
  - `Main/Notification/*` leverages TimingFramework for toast animations.
- QR Codes
  - ZXing (`com.google.zxing`) renders `BufferedImage` then displayed via `ImageIcon`.
- Encoding
  - Use UTF‑8 in project and DB. If you see replacement characters (�), ensure the JDBC URL includes `useUnicode=true&characterEncoding=UTF-8`.
- Credentials
  - Credentials are currently hard‑coded in the codebase for demonstration. For production, externalize them (environment variables or an unversioned `config.properties`).

License
- Academic project — no explicit license is defined in this repository.