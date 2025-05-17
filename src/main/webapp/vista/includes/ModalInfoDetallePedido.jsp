<%@page import="Util.Constantes"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<div class="modal fade" id="modalInfoDetallePed" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header bg-danger text-white">
                <h5 class="modal-title fs-5">::: Detalle Pedido :::</h5>
            </div>
            <div class="modal-body">
                <table style="">
                    <tr>
                        <td style="font-weight: bold;text-align: right;padding: 6px;">Nro Pedido:</td>
                        <td>${pedido.nroPed}</td>
                    </tr>
                    <tr>
                        <td style="font-weight: bold;text-align: right;padding: 6px;">Cliente:</td>
                        <td>${pedido.cliente.nombres} ${pedido.cliente.apellidos}</td>
                    </tr>  
                    <tr>
                        <td style="font-weight: bold;text-align: right;padding: 6px;">Fecha Pedido:</td>
                        <td>${pedido.fecha}</td>
                    </tr>  
                    <tr>
                        <td style="font-weight: bold;text-align: right;padding: 6px;">Estado:</td>
                        <td>${pedido.estado}</td>
                    </tr>                
                </table>

                <div class="row mt-2">
                    <div class="col-lg-12">                 
                        <table class="table table-bordered">
                            <thead class="thead-light">
                                <tr class="text-center">
                                    <c:if test="${ver == 1}">
                                        <th colspan="2"></th>     
                                        </c:if>
                                        <c:if test="${ver == 0}">
                                        <th ></th>     
                                        </c:if>

                                    <th>Producto</th>
                                    <th>Precio (S/)</th>
                                    <th>Cantidad</th>                       
                                    <th>Total (S/)</th>     
                                    <th>Atendido</th>
                                </tr>
                            </thead>
                            <tbody >
                                <c:forEach var="item" items="${detalles}" varStatus="loop"> 
                                    <tr class="text-center">    
                                        <c:if test="${ver == 1}">
                                            <td>
                                                <a href="#"  class="btn btn-info btn-sm btn-circle" title="Ver información empresa">
                                                    <b>${(loop.index+1)}</b>
                                                </a>
                                            </td>
                                        </c:if>


                                        <td>
                                            <img  src="${item.producto.imagen}" class="card-img-top" style="width: 80px;height: 60px;">
                                        </td>
                                        <td>${item.producto.nombre}</td>             
                                        <td>${item.precio}</td>
                                        <td>${item.cantidad}</td>   
                                        <td> <fmt:formatNumber type = "number" 
                                                          maxFractionDigits = "2" value= "${item.precio * item.cantidad}" /></td>     
                                        <td>
                                            <c:if test="${item.atendido == Constantes.ESTADO_DET_ATENDIDO}">
                                                <i class="fa fa-check-circle" style="color: #009933; cursor: pointer;"  title="Atendido"></i>
                                            </c:if>
                                            <c:if test="${item.atendido == Constantes.ESTADO_DET_NO_ATENDIDO}">
                                                <i class="fa fa-times-circle" style="color: #cc0000;cursor: pointer; "  title="No atendido"></i>
                                            </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>                    
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-bs-dismiss="modal" style="border-color: #ccc">Cerrar</button>
            </div>
        </div>
    </div>
</div>