const BASE_URL = 'http://localhost:8080/api/v1';

const productosContainer = document.getElementById('productos-container');
const welcomeUser = document.getElementById('welcomeUser');
const btnLogin = document.getElementById('btn-login');
const btnCerrarSesion = document.querySelector('button[onclick="cerrarSesion()"]');

const cartSidebar = document.getElementById('cart-sidebar');
const cartOverlay = document.getElementById('cart-overlay');
const openCartBtn = document.getElementById('open-cart');
const closeCartBtn = document.getElementById('close-cart');

const carritoItemsContainer = document.getElementById('carrito-items');
const cartCounterNav = document.getElementById('cart-counter-nav');
const cartCounterSidebar = document.getElementById('cart-counter');
const carritoTotalEl = document.getElementById('carrito-total');
const vaciarCarritoBtn = document.getElementById('vaciar-carrito');
const realizarCompraBtn = document.getElementById('realizar-compra');

const compraExitosaModalEl = document.getElementById('compraExitosaModal');
let compraExitosaModal; // Declaración sin asignar aún

let productos = [];
let carrito = [];

// --- Usuario y Login ---
function mostrarUsuario() {
    const user = localStorage.getItem('username') || 'Invitado';
    welcomeUser.textContent = 'Usuario: ' + user;
    if (user === 'Invitado') {
        btnLogin.style.display = 'inline-block';
        btnCerrarSesion.style.display = 'none';
    } else {
        btnLogin.style.display = 'none';
        btnCerrarSesion.style.display = 'inline-block';
    }
}

async function pedirLogin() {
    return new Promise((resolve, reject) => {
        const user = prompt("Usuario:");
        const pass = prompt("Contraseña:");

        if (!user || !pass) {
            alert("Login cancelado");
            return reject("Cancelado");
        }

        const authHeader = "Basic " + btoa(user + ":" + pass);

        // Probar autenticación con un endpoint cualquiera que requiera auth, p.ej. /products
        fetch(`${BASE_URL}/products`, {
            headers: { Authorization: authHeader }
        })
            .then(res => {
                if (!res.ok) throw new Error("Credenciales incorrectas");
                localStorage.setItem("authHeader", authHeader);
                localStorage.setItem("username", user);
                mostrarUsuario();
                resolve(authHeader);
            })
            .catch(err => {
                localStorage.removeItem("authHeader");
                localStorage.removeItem("username");
                alert("Login fallido: " + err.message);
                reject(err);
            });
    });
}

function cerrarSesion() {
    localStorage.removeItem("authHeader");
    localStorage.removeItem("username");
    localStorage.removeItem("carrito");
    carrito = [];
    mostrarUsuario();
    actualizarContadores();
    renderizarCarrito();
    alert("Sesión cerrada");

    // Solo recarga productos sin auth (se muestran igual)
    cargarProductos();
}

window.cerrarSesion = cerrarSesion;

// --- Productos ---

// Modificado para cargar productos SIN auth (no se pasa header)
async function cargarProductos(authHeader) {
    try {
        const headers = {};
        if (authHeader) headers.Authorization = authHeader;

        const res = await fetch(`${BASE_URL}/products`, { headers });

        if (!res.ok) {
            if (res.status === 401) throw new Error("No autorizado");
            throw new Error("Error al obtener productos");
        }
        productos = await res.json();
        renderizarProductos();
    } catch (error) {
        alert(error.message);
        // En caso de error 401, forzar login y recarga productos con auth
        if (error.message === "No autorizado") {
            try {
                const newAuth = await pedirLogin();
                await cargarProductos(newAuth);
            } catch {
                productosContainer.innerHTML = '<p>Debe iniciar sesión para ver productos.</p>';
            }
        }
    }
}

function renderizarProductos() {
    productosContainer.innerHTML = '';
    productos.forEach(prod => {
        const card = document.createElement('div');
        card.className = 'card';
        card.style.width = '18rem';
        card.innerHTML = `
      <img src="${prod.image || 'https://via.placeholder.com/150'}" class="card-img-top" alt="${prod.name}">
      <div class="card-body d-flex flex-column">
        <h5 class="card-title">${prod.name}</h5>
        <p class="card-text">$${prod.price}</p>
        <button class="btn btn-primary mt-auto btn-agregar" data-id="${prod.id}">Agregar al carrito</button>
      </div>
    `;
        productosContainer.appendChild(card);
    });

    document.querySelectorAll('.btn-agregar').forEach(btn => {
        btn.addEventListener('click', () => {
            const id = parseInt(btn.dataset.id);
            const producto = productos.find(p => p.id === id);
            if (producto) agregarAlCarrito(producto);
        });
    });
}

// --- Carrito ---
function cargarCarritoDesdeStorage() {
    carrito = JSON.parse(localStorage.getItem('carrito')) || [];
}

function guardarCarritoEnStorage() {
    localStorage.setItem('carrito', JSON.stringify(carrito));
}

// Modificado para pedir login si NO está logueado al agregar al carrito
async function agregarAlCarrito(producto) {
    const authHeader = localStorage.getItem('authHeader');
    if (!authHeader) {
        alert('Debes iniciar sesión para agregar productos al carrito');
        try {
            const newAuth = await pedirLogin();
            if (newAuth) {
                cargarProductos(newAuth); // Opcional: recargar productos con auth
                mostrarUsuario();
            }
        } catch {
            return; // login cancelado, no agrega producto
        }
    }

    cargarCarritoDesdeStorage();
    const index = carrito.findIndex(item => item.id === producto.id);
    if (index >= 0) {
        carrito[index].cantidad++;
    } else {
        carrito.push({ ...producto, cantidad: 1 });
    }
    guardarCarritoEnStorage();
    actualizarContadores();
    renderizarCarrito();
    alert(`Producto "${producto.name}" agregado al carrito`);
}

function actualizarContadores() {
    const count = carrito.reduce((acc, item) => acc + item.cantidad, 0);
    cartCounterNav.textContent = count;
    cartCounterSidebar.textContent = count;
}

function renderizarCarrito() {
    carritoItemsContainer.innerHTML = '';
    if (carrito.length === 0) {
        carritoItemsContainer.innerHTML = '<p>El carrito está vacío.</p>';
        carritoTotalEl.textContent = '0';
        return;
    }
    let total = 0;
    carrito.forEach(item => {
        const itemDiv = document.createElement('div');
        itemDiv.className = 'd-flex justify-content-between align-items-center mb-2';

        const infoDiv = document.createElement('div');
        infoDiv.className = 'item-info';
        infoDiv.textContent = `${item.name} x${item.cantidad} - $${item.price * item.cantidad}`;

        const removeBtn = document.createElement('button');
        removeBtn.className = 'remove-item btn btn-sm btn-outline-danger';
        removeBtn.textContent = '×';
        removeBtn.title = 'Eliminar producto';
        removeBtn.addEventListener('click', () => {
            eliminarDelCarrito(item.id);
        });

        itemDiv.appendChild(infoDiv);
        itemDiv.appendChild(removeBtn);
        carritoItemsContainer.appendChild(itemDiv);

        total += item.price * item.cantidad;
    });
    carritoTotalEl.textContent = total.toFixed(2);
}

function eliminarDelCarrito(id) {
    carrito = carrito.filter(item => item.id !== id);
    guardarCarritoEnStorage();
    actualizarContadores();
    renderizarCarrito();
}

function vaciarCarrito() {
    carrito = [];
    guardarCarritoEnStorage();
    actualizarContadores();
    renderizarCarrito();
}

// Modificado para pedir login si no está al realizar la compra
async function realizarCompra() {
    if (carrito.length === 0) {
        alert('El carrito está vacío');
        return;
    }

    const authHeader = localStorage.getItem('authHeader');
    if (!authHeader) {
        alert('Debe iniciar sesión para realizar la compra');
        try {
            const newAuth = await pedirLogin();
            if (!newAuth) return;
        } catch {
            return;
        }
    }

    const clientId = 1; // o lo que corresponda en tu sistema

    // Preparar payload para tu backend
    const orderLines = carrito.map(item => ({
        productId: item.id,
        quantity: item.cantidad
    }));

    const payload = { clientId, lineRequestDTOList: orderLines };

    try {
        const res = await fetch(`${BASE_URL}/orders`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': localStorage.getItem('authHeader')
            },
            body: JSON.stringify(payload)
        });
        if (!res.ok) {
            const text = await res.text();
            throw new Error(text || 'Error al realizar la compra');
        }
        const total = carrito.reduce((acc, item) => acc + item.price * item.cantidad, 0);
        vaciarCarrito();
        compraExitosaModalEl.querySelector('#modal-total').textContent = total.toFixed(2);
        compraExitosaModal.show();
        cerrarCarrito();
    } catch (err) {
        alert('Error: ' + err.message);
    }
}

// --- Sidebar carrito ---

function abrirCarrito() {
    cartSidebar.classList.add('open');
    cartOverlay.classList.add('show');
}

function cerrarCarrito() {
    cartSidebar.classList.remove('open');
    cartOverlay.classList.remove('show');
}

// --- Eventos ---

window.addEventListener('DOMContentLoaded', async () => {
    mostrarUsuario();
    cargarCarritoDesdeStorage();
    actualizarContadores();
    renderizarCarrito();

    compraExitosaModal = new bootstrap.Modal(compraExitosaModalEl);

    // Cargar productos sin auth para que estén visibles siempre
    await cargarProductos();

    btnLogin.addEventListener('click', async () => {
        try {
            const newAuth = await pedirLogin();
            await cargarProductos(newAuth);
            cargarCarritoDesdeStorage();
            actualizarContadores();
            renderizarCarrito();
        } catch {
            // login cancelado
        }
    });

    openCartBtn.addEventListener('click', e => {
        e.preventDefault();
        abrirCarrito();
    });

    closeCartBtn.addEventListener('click', cerrarCarrito);
    cartOverlay.addEventListener('click', cerrarCarrito);

    vaciarCarritoBtn.addEventListener('click', () => {
        if (confirm('¿Seguro que quieres vaciar el carrito?')) {
            vaciarCarrito();
        }
    });

    realizarCompraBtn.addEventListener('click', realizarCompra);
});
