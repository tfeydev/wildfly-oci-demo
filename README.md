# Product App – WildFly + Oracle Demo

This is a demo Java web application (`WAR`) that connects to an Oracle Database and is deployed on **WildFly**.  
It demonstrates how to build, package, and deploy a simple servlet-based application with JDBC integration.

---

## 📂 Project Structure

```
product-app/
├── pom.xml                     # Maven build configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── techthor/
│   │   │       └── ProductServlet.java   # Main servlet connecting to Oracle DB
│   │   ├── resources/
│   │   │   └── META-INF/
│   │   └── webapp/
│   │       ├── index.jsp      # Welcome page
│   │       └── WEB-INF/
│   │           └── web.xml    # Servlet configuration
└── target/
    └── product-app-1.0-SNAPSHOT.war   # Built deployable WAR
```

---

## ⚙️ Step 1 – Requirements

- **Java 17 or 21 (LTS)** installed (`JAVA_HOME` set)
- **Maven 3.9+**
- **WildFly 30+**
- **Oracle Database XE / Autonomous DB**
- **Oracle JDBC driver** (`ojdbc11.jar`)

---

## ⚙️ Step 2 – Configure Oracle JDBC Module in WildFly

WildFly requires JDBC drivers to be added as modules.

1. Go to modules folder:
   ```bash
   cd $WILDFLY_HOME/modules/system/layers/base
   ```

2. Create directory structure for Oracle driver:
   ```bash
   mkdir -p com/oracle/main
   cp /path/to/ojdbc11.jar com/oracle/main/
   ```

3. Create `module.xml` inside `com/oracle/main/`:
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <module xmlns="urn:jboss:module:1.9" name="com.oracle">
       <resources>
           <resource-root path="ojdbc11.jar"/>
       </resources>
       <dependencies>
           <module name="javax.api"/>
           <module name="javax.transaction.api"/>
       </dependencies>
   </module>
   ```

---

## ⚙️ Step 3 – Configure Datasource in WildFly

1. Start WildFly standalone:
   ```bash
   $WILDFLY_HOME/bin/standalone.sh
   ```

2. Go to `http://localhost:9990` (WildFly Admin Console).

3. Add a **Datasource**:
    - Name: `ProductDS`
    - JNDI: `java:/jdbc/ProductDS`
    - Driver: `com.oracle`
    - URL: `jdbc:oracle:thin:@//localhost:1521/XEPDB1` (adjust for your DB)
    - User: `ec`
    - Password: `your_password`

---

## ⚙️ Step 4 – Build and Package the WAR

Build the project using Maven:
```bash
mvn clean package
```

This will generate:
```
target/product-app-1.0-SNAPSHOT.war
```

---

## ⚙️ Step 5 – Deploy to WildFly

1. Copy the WAR to WildFly deployments folder:
   ```bash
   cp target/product-app-1.0-SNAPSHOT.war $WILDFLY_HOME/standalone/deployments/
   ```

2. Check logs for successful deployment:
   ```bash
   tail -f $WILDFLY_HOME/standalone/log/server.log
   ```

3. Access in browser:
   ```
   http://localhost:8080/product-app/
   ```

---

## ⚙️ Step 6 – Verify the Servlet

If everything is correct, you should see:

```
Product Servlet is running!
This is a demo app deployed on WildFly.
```

---

## ❓ Why this setup?

- **WAR packaging** → standard for deploying web apps on application servers like WildFly.
- **Servlet + JSP** → minimal, portable web layer to demonstrate Oracle DB integration.
- **Oracle JDBC as WildFly Module** → ensures container-managed datasource.
- **Maven** → dependency and build management.
- **WildFly** → production-grade Java EE/Jakarta EE application server.

This setup mirrors **enterprise-grade deployments**, but simplified for demo purposes.