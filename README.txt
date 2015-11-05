# Proyecto-Base-2
Universidad Nacional de Río Cuarto
Facultad de Cs. Exactas Físico Químicas y Naturales
Departamento de Computación
BASES DATOS II (Optativa, extracurricular y de postgrado) - AÑO 2015
Proyecto Final.
El proyecto consiste en el desarrollo de una herramienta que permita realizar la
comparación estructural de dos base de datos de un mismo tipo de motor (el tipo de la
base de datos es a elección del grupo). El proyecto debe ser desarrollado en el lenguaje
Java o el que el grupo seleccione con acuerdo de los docentes de la materia. La
herramienta deberá generar un informe con las diferencias que se encuentren entre ambas
bases de datos. Para la realización del informe se deberá tener en cuenta:
o Nivel Tabla, indicar si tienen las mismas tablas, y si no es así, especificar las
tablas comunes, y por cada base de datos que tablas adicionales contienen.
¨ Para diferencias entre tablas del mismo nombre indicar:
· Si tienen las mismas columnas, y si no es así, especificar las columnas
adicionales que poseen.
· Si teniendo el mismo nombre de columna, pero las columnas son de
distinto tipo, esto también se debe especificar.
· Diferencia de Triggers.
· Diferencias entre claves primarias, únicas y foráneas e Índices.
o Nivel Procedimientos, indicar si tienen los mismos procedimientos/funciones, y si
no es así, especificar por cada base de datos que procedimiento/funciones
adicionales contienen. Además, para los procedimientos con el mismo nombre
indicar si hay diferencias en los perfiles.
Consideraciones:
· Para el desarrollo del proyecto se deberá utilizar la API JDBC.
· Para obtener información sobre los objetos de la base de datos (los metadatos),
que no pueda obtener mediante la API JDBC(por medio de sus componentes para
extraer metadatos), lo deberá hacer accediendo a las tablas o vistas del sistema.
· El sistema a desarrollar debe solicitar al usuario: host, usuario, contraseña y
nombre de las bases de datos para acceder a ellas (pueden ser diferentes tanto el
host como el resto de los datos). Los valores por defecto deben ser leído de un
archivo de configuración.
· Los grupos pueden ser de 2 o 3 alumnos.
· Fecha de entrega: inicios de Noviembre de 2015 (a definir).
