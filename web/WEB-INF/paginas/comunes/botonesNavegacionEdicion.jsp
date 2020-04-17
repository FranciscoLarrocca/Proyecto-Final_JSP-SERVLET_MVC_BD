<%-- 
    Document   : botonesNavegacionEdicion
    Created on : Apr 16, 2020, 6:20:58 PM
    Author     : Francisco Larrocca
--%>
<section id="actions" class="py-4 mb-4 bg-light">
    <div class="container">
        <div class="row">
            <div class="col-md-3">
                <a href="index.jsp" class="btn btn-light btn-block">
                    <i class="fas fa-arrow-left"></i> Regresar al inicio
                </a> 
            </div>
            <div class="col-md-3">
                <button type="submit" class="btn btn-success btn-block">
                    <i class="fas fa-check"></i> Guardar cliente
                </button>
            </div>
            <div class="col-md-3">
                <!-- La peticion al estar en un link, es una peticon GET -->
                <a href="${pageContext.request.contextPath}/ServletControlador?accion=eliminar&idCliente=${cliente.idCliente}"
                   class="btn btn-danger btn-block">
                    <i class="fas fa-trash"></i> Eliminar cliente
                </a>
            </div>
        </div>
    </div>

</section>