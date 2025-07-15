window.addEventListener('DOMContentLoaded', () => {
    const BASE = 'http://localhost:8080/api/v1';

    // Secciones y botones
    const btnP = document.getElementById('btnProducts');
    const btnO = document.getElementById('btnOrders');
    const secP = document.getElementById('sectionProducts');
    const secO = document.getElementById('sectionOrders');
    btnP.onclick = () => { secP.style.display = 'block'; secO.style.display = 'none'; };
    btnO.onclick = () => { secP.style.display = 'none'; secO.style.display = 'block'; };

    // PRODUCTOS
    const pForm = document.getElementById('productForm');
    const pMsg  = document.getElementById('msgProduct');
    const pList = document.getElementById('productList');

    async function loadProducts() {
        const res = await fetch(`${BASE}/products`, { credentials: "include" });
        if (!res.ok) {
            pMsg.textContent = 'Error al obtener productos: ' + res.statusText;
            return;
        }
        const data = await res.json();
        pList.innerHTML = '';
        data.forEach(p => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${p.name}</td>
                <td>${p.price}</td>
                <td>${p.stock}</td>
                <td><img src="${p.image}" width="40" /></td>
                <td>
                  <button onclick='editProd(${JSON.stringify(p)})'>Editar</button>
                  <button onclick='delProd(${p.id})'>Eliminar</button>
                </td>`;
            pList.appendChild(tr);
        });
    }

    pForm.addEventListener('submit', async e => {
        e.preventDefault();
        const product = {
            name: pForm.name.value,
            price: parseFloat(pForm.price.value),
            stock: parseInt(pForm.stock.value),
            description: pForm.description.value,
            image: pForm.image.value
        };
        const id = pForm.productId.value;
        const method = id ? 'PUT' : 'POST';
        const url = `${BASE}/products${id ? `/${id}` : ''}`;
        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            credentials: "include",
            body: JSON.stringify(product)
        });
        pMsg.textContent = res.ok ? 'Producto guardado' : 'Error';
        loadProducts();
        pForm.reset();
    });

    window.editProd = p => {
        pForm.productId.value = p.id;
        pForm.name.value = p.name;
        pForm.price.value = p.price;
        pForm.stock.value = p.stock;
        pForm.description.value = p.description;
        pForm.image.value = p.image;
    };

    window.delProd = async id => {
        if (!confirm('Eliminar producto?')) return;
        const res = await fetch(`${BASE}/products/${id}`, {
            method: 'DELETE',
            credentials: "include"
        });
        if (!res.ok) alert('Error al eliminar producto');
        loadProducts();
    };

    // ÓRDENES
    const oForm = document.getElementById('orderForm');
    const oMsg  = document.getElementById('msgOrder');
    const oList = document.getElementById('orderList');

    async function loadOrders() {
        const res = await fetch(`${BASE}/orders`, { credentials: "include" });
        if (!res.ok) {
            oMsg.textContent = 'Error al obtener órdenes: ' + res.statusText;
            return;
        }
        const data = await res.json();
        oList.innerHTML = '';
        data.forEach(o => {
            const lines = o.orderLines.map(l =>
                `${l.quantity} x ${l.product?.name || 'Producto eliminado'}`
            ).join('<br>');

            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${o.id}</td>
                <td>${o.clientName} ${o.clientSurname}</td>
                <td>${lines}</td>
                <td>${o.state}</td>
                <td>$${o.total.toFixed(2)}</td>
                <td>
                  <button onclick='editOrd(${JSON.stringify(o)})'>Editar</button>
                  <button onclick='delOrd(${o.id})'>Eliminar</button>
                </td>`;
            oList.appendChild(tr);
        });
    }

    oForm.addEventListener('submit', async e => {
        e.preventDefault();
        const orderObj = {
            clientId: parseInt(oForm.clientId.value),
            orderLines: JSON.parse(oForm.orderLines.value) // [{ productId: 1, quantity: 2 }]
        };
        const id = oForm.orderId.value;
        const method = id ? 'PUT' : 'POST';
        const url = `${BASE}/orders${id ? `/${id}` : ''}`;
        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            credentials: "include",
            body: JSON.stringify(orderObj)
        });
        oMsg.textContent = res.ok ? 'Orden guardada' : 'Error';
        loadOrders();
        oForm.reset();
    });

    window.editOrd = o => {
        oForm.orderId.value = o.id;
        oForm.clientId.value = o.clientId || '';
        oForm.orderLines.value = JSON.stringify(
            o.orderLines.map(l => ({ productId: l.product?.id || null, quantity: l.quantity }))
        );
    };

    window.delOrd = async id => {
        if (!confirm('Eliminar orden?')) return;
        const res = await fetch(`${BASE}/orders/${id}`, {
            method: 'DELETE',
            credentials: "include"
        });
        if (!res.ok) alert('Error al eliminar orden');
        loadOrders();
    };

    // Inicializar carga
    loadProducts();
    loadOrders();
});
