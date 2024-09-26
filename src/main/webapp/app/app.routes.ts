import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UsuarioListComponent } from './usuario/usuario-list.component';
import { UsuarioAddComponent } from './usuario/usuario-add.component';
import { UsuarioEditComponent } from './usuario/usuario-edit.component';
import { ProveedorListComponent } from './proveedor/proveedor-list.component';
import { ProveedorAddComponent } from './proveedor/proveedor-add.component';
import { ProveedorEditComponent } from './proveedor/proveedor-edit.component';
import { PedidosCompraListComponent } from './pedidos-compra/pedidos-compra-list.component';
import { PedidosCompraAddComponent } from './pedidos-compra/pedidos-compra-add.component';
import { PedidosCompraEditComponent } from './pedidos-compra/pedidos-compra-edit.component';
import { ProductoListComponent } from './producto/producto-list.component';
import { ProductoAddComponent } from './producto/producto-add.component';
import { ProductoEditComponent } from './producto/producto-edit.component';
import { AeronaveListComponent } from './aeronave/aeronave-list.component';
import { AeronaveAddComponent } from './aeronave/aeronave-add.component';
import { AeronaveEditComponent } from './aeronave/aeronave-edit.component';
import { ContactoProveedorListComponent } from './contacto-proveedor/contacto-proveedor-list.component';
import { ContactoProveedorAddComponent } from './contacto-proveedor/contacto-proveedor-add.component';
import { ContactoProveedorEditComponent } from './contacto-proveedor/contacto-proveedor-edit.component';
import { TransaccionEventoListComponent } from './transaccion-evento/transaccion-evento-list.component';
import { TransaccionEventoAddComponent } from './transaccion-evento/transaccion-evento-add.component';
import { TransaccionEventoEditComponent } from './transaccion-evento/transaccion-evento-edit.component';
import { TransaccionListComponent } from './transaccion/transaccion-list.component';
import { TransaccionAddComponent } from './transaccion/transaccion-add.component';
import { TransaccionEditComponent } from './transaccion/transaccion-edit.component';
import { PedidosProductoListComponent } from './pedidos-producto/pedidos-producto-list.component';
import { PedidosProductoAddComponent } from './pedidos-producto/pedidos-producto-add.component';
import { PedidosProductoEditComponent } from './pedidos-producto/pedidos-producto-edit.component';
import { ErrorComponent } from './error/error.component';


export const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
    title: $localize`:@@home.index.headline:Welcome to your new app!`
  },
  {
    path: 'usuarios',
    component: UsuarioListComponent,
    title: $localize`:@@usuario.list.headline:Usuarios`
  },
  {
    path: 'usuarios/add',
    component: UsuarioAddComponent,
    title: $localize`:@@usuario.add.headline:Add Usuario`
  },
  {
    path: 'usuarios/edit/:usrId',
    component: UsuarioEditComponent,
    title: $localize`:@@usuario.edit.headline:Edit Usuario`
  },
  {
    path: 'proveedor',
    component: ProveedorListComponent,
    title: $localize`:@@proveedor.list.headline:Proveedor`
  },
  {
    path: 'proveedor/add',
    component: ProveedorAddComponent,
    title: $localize`:@@proveedor.add.headline:Add Proveedor`
  },
  {
    path: 'proveedor/edit/:pveId',
    component: ProveedorEditComponent,
    title: $localize`:@@proveedor.edit.headline:Edit Proveedor`
  },
  {
    path: 'pedidosCompras',
    component: PedidosCompraListComponent,
    title: $localize`:@@pedidosCompra.list.headline:Pedidos Compras`
  },
  {
    path: 'pedidosCompras/add',
    component: PedidosCompraAddComponent,
    title: $localize`:@@pedidosCompra.add.headline:Add Pedidos Compra`
  },
  {
    path: 'pedidosCompras/edit/:pcaId',
    component: PedidosCompraEditComponent,
    title: $localize`:@@pedidosCompra.edit.headline:Edit Pedidos Compra`
  },
  {
    path: 'producto',
    component: ProductoListComponent,
    title: $localize`:@@producto.list.headline:Producto`
  },
  {
    path: 'producto/add',
    component: ProductoAddComponent,
    title: $localize`:@@producto.add.headline:Add Producto`
  },
  {
    path: 'producto/edit/:proId',
    component: ProductoEditComponent,
    title: $localize`:@@producto.edit.headline:Edit Producto`
  },
  {
    path: 'aeronaves',
    component: AeronaveListComponent,
    title: $localize`:@@aeronave.list.headline:Aeronaves`
  },
  {
    path: 'aeronaves/add',
    component: AeronaveAddComponent,
    title: $localize`:@@aeronave.add.headline:Add Aeronave`
  },
  {
    path: 'aeronaves/edit/:anvId',
    component: AeronaveEditComponent,
    title: $localize`:@@aeronave.edit.headline:Edit Aeronave`
  },
  {
    path: 'contactoProveedor',
    component: ContactoProveedorListComponent,
    title: $localize`:@@contactoProveedor.list.headline:Contacto Proveedor`
  },
  {
    path: 'contactoProveedor/add',
    component: ContactoProveedorAddComponent,
    title: $localize`:@@contactoProveedor.add.headline:Add Contacto Proveedor`
  },
  {
    path: 'contactoProveedor/edit/:cveId',
    component: ContactoProveedorEditComponent,
    title: $localize`:@@contactoProveedor.edit.headline:Edit Contacto Proveedor`
  },
  {
    path: 'transaccionEvento',
    component: TransaccionEventoListComponent,
    title: $localize`:@@transaccionEvento.list.headline:Transaccion Evento`
  },
  {
    path: 'transaccionEvento/add',
    component: TransaccionEventoAddComponent,
    title: $localize`:@@transaccionEvento.add.headline:Add Transaccion Evento`
  },
  {
    path: 'transaccionEvento/edit/:tvoId',
    component: TransaccionEventoEditComponent,
    title: $localize`:@@transaccionEvento.edit.headline:Edit Transaccion Evento`
  },
  {
    path: 'transaccions',
    component: TransaccionListComponent,
    title: $localize`:@@transaccion.list.headline:Transaccions`
  },
  {
    path: 'transaccions/add',
    component: TransaccionAddComponent,
    title: $localize`:@@transaccion.add.headline:Add Transaccion`
  },
  {
    path: 'transaccions/edit/:tceId',
    component: TransaccionEditComponent,
    title: $localize`:@@transaccion.edit.headline:Edit Transaccion`
  },
  {
    path: 'pedidosProducto',
    component: PedidosProductoListComponent,
    title: $localize`:@@pedidosProducto.list.headline:Pedidos Productoes`
  },
  {
    path: 'pedidosProducto/add',
    component: PedidosProductoAddComponent,
    title: $localize`:@@pedidosProducto.add.headline:Add Pedidos Producto`
  },
  {
    path: 'pedidosProducto/edit/:pptId',
    component: PedidosProductoEditComponent,
    title: $localize`:@@pedidosProducto.edit.headline:Edit Pedidos Producto`
  },
  {
    path: 'error',
    component: ErrorComponent,
    title: $localize`:@@error.headline:Error`
  },
  {
    path: '**',
    component: ErrorComponent,
    title: $localize`:@@notFound.headline:Page not found`
  }
];
