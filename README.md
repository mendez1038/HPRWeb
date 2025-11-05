# 🪐 HPR Web

> Plataforma de distribución digital de contenidos desarrollada en **Java EE (Servlets + JSP)**, con arquitectura MVC, base de datos MySQL y autenticación segura con Jasypt.

---

## 🧩 Descripción general

**HPR Web** es una aplicación web full-stack que simula una plataforma tipo *Netflix / Spotify*, donde los usuarios pueden:
- Registrarse e iniciar sesión.
- Explorar y buscar contenido por filtros.
- Añadir productos a **favoritos** o al **carrito**.
- Realizar **compras** y generar pedidos.
- Consultar su **historial** y **biblioteca de contenidos** adquiridos.
- Editar o eliminar su cuenta.

El proyecto está orientado a aprender arquitectura **MVC**, patrones **DAO/Service/Controller**, y buenas prácticas en desarrollo web con **Java EE**.

---

## 🚀 Funcionalidades principales

✅ Registro, login y logout de usuarios.  
✅ Búsqueda avanzada de contenido (por idioma, tipo, categoría, edad...).  
✅ Sistema de favoritos persistente.  
✅ Carrito de compra en sesión, con control de duplicados y total automático.  
✅ Proceso de **checkout** completo (inserta pedido y líneas en BD).  
✅ Historial de pedidos y biblioteca personal de contenidos comprados.  
✅ Eliminación segura de cuenta con validación de contraseña.  
✅ Interfaz internacionalizada (i18n) con JSTL y mensajes en varios idiomas.  
✅ Arquitectura modular y capas bien separadas.

---

## 🧱 Tecnologías utilizadas

| Capa | Tecnologías |
|------|--------------|
| **Frontend** | JSP, JSTL, HTML5, CSS3, JavaScript |
| **Backend** | Java 17, Servlets, JDBC |
| **Persistencia** | MySQL, DAO Pattern |
| **Seguridad** | Encriptación con Jasypt (`StrongPasswordEncryptor`) |
| **Servidor** | Apache Tomcat 9 |
| **Utilidades** | Log4j2, Apache Commons Lang |
| **IDE recomendado** | Eclipse o IntelliJ IDEA |

---

## 🗂️ Estructura del proyecto

com.david.training.model -> Entidades y objetos de valor
com.david.training.dao -> Interfaces DAO
com.david.training.dao.impl -> Implementaciones DAO con JDBC
com.david.training.service -> Interfaces de servicios
com.david.training.service.impl -> Lógica de negocio y validaciones
com.hpr.web.controller -> Servlets y controladores web (MVC)
com.hpr.web.util -> Utilidades (cookies, sesión, validación, etc.)
com.hpr.web.model -> Modelos de soporte para la vista (Carrito, LineaCarrito...)


---

## 🧾 Base de datos

**Motor:** MySQL (InnoDB)  
**Nombre de BD:** `HPR`

### Tablas principales
- `USUARIO`  
- `CONTENIDO`  
- `CONTENIDO_IDIOMA`  
- `PEDIDO`  
- `LINEAPEDIDO`  
- `FAVORITO`  
- `DESCUENTO`  
- `TIPO_CONTENIDO`

Ejemplo de relaciones:
- Un `PEDIDO` pertenece a un `USUARIO`.
- Un `PEDIDO` tiene muchas `LINEAPEDIDO`.
- Cada `LINEAPEDIDO` se asocia a un `CONTENIDO`.

---

## ⚙️ Instalación y ejecución

1️⃣ Clonar el repositorio
```bash
git clone https://github.com/tuusuario/hpr-web.git

2️⃣ Importar el proyecto

Importar como Dynamic Web Project en Eclipse o IntelliJ IDEA.

3️⃣ Configurar la base de datos

Crear una base de datos llamada HPR.

Ejecutar el script SQL incluido en la carpeta /db/.

4️⃣ Configurar conexión JDBC

Editar las credenciales en:

/src/com/david/training/dao/util/ConnectionManager.java

5️⃣ Desplegar en Apache Tomcat 9

Ejecutar desde Eclipse con:

Run As → Run on Server → Apache Tomcat 9

6️⃣ Acceder desde el navegador
http://localhost:8080/HPRWEB/

🧠 Aprendizajes clave

Diseño y desarrollo de aplicaciones Java EE con Servlets y JSP.

Patrón MVC con capas DAO, Service y Controller.

Manejo de sesiones, cookies y autenticación segura.

Internacionalización (i18n) con JSTL.

Validaciones y manejo robusto de excepciones.

Arquitectura limpia, escalable y fácilmente extensible.

📜 Licencia

Proyecto personal con fines educativos.
Puedes usarlo como referencia o punto de partida para tus propios desarrollos.

👨‍💻 Autor

David Méndez Martínez
📧 dmendez1038@gmail.com
