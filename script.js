// Menyimpan data barang ke localStorage
function saveToLocalStorage(dataKey, data) {
    localStorage.setItem(dataKey, JSON.stringify(data));
}

// Mengambil data barang dari localStorage
function getFromLocalStorage(dataKey) {
    return JSON.parse(localStorage.getItem(dataKey)) || [];
}

// Fungsi untuk memuat data awal ke tabel di daftarbarang.html
function loadTableData(tableId, dataKey) {
    const tableBody = document.querySelector(`#${tableId} tbody`);
    const data = getFromLocalStorage(dataKey);

    tableBody.innerHTML = ''; // Bersihkan tabel sebelum memuat data

    data.forEach((item, index) => {
        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>${item.kode}</td>
                <td>${item.nama}</td>
                <td>${item.kategori}</td>
                <td>${item.stok}</td>
                <td>${item.harga}</td>
                <td>${item.tanggalMasuk}</td>
                <td>${item.tanggalKeluar}</td>
                <td>${item.tanggalExpired}</td>
                <td>
                    <button onclick="editItem(${index}, 'barangData')">Edit</button>
                    <button onclick="deleteItem(${index}, 'barangData', '${tableId}')">Hapus</button>
                </td>
            </tr>
        `;
        tableBody.innerHTML += row;
    });
}

// Fungsi untuk menyaring tabel berdasarkan input pencarian
function searchTable(searchInputId, tableId) {
    const searchInput = document.getElementById(searchInputId).value.toLowerCase();
    const rows = document.querySelectorAll(`#${tableId} tbody tr`);

    rows.forEach(row => {
        const isVisible = Array.from(row.cells).some(cell =>
            cell.textContent.toLowerCase().includes(searchInput)
        );
        row.style.display = isVisible ? '' : 'none';
    });
}

// Fungsi untuk menyimpan data dari form ke localStorage
function saveFormData(formId, dataKey) {
    const form = document.getElementById(formId);
    const newItem = {
        kode: form['kode-barang'].value,
        nama: form['nama-barang'].value,
        kategori: form['kategori-barang'].value || 'Bahan Kue',
        stok: form['stok-barang'].value,
        harga: form['harga-barang'].value,
        tanggalMasuk: form['tanggal-barang-masuk'].value,
        tanggalKeluar: form['tanggal-barang-keluar'].value,
        tanggalExpired: form['tanggal-barang-expired'].value
    };

    const data = getFromLocalStorage(dataKey);
    data.push(newItem);
    saveToLocalStorage(dataKey, data);

    alert('Data berhasil disimpan!');
    window.location.href = 'daftarbarang.html'; // Kembali ke halaman daftar barang
}

// Fungsi untuk memuat data dari URL query string (edit mode)
function loadFormDataFromURL(formId) {
    const params = new URLSearchParams(window.location.search);
    if (params.has('data')) {
        const data = JSON.parse(decodeURIComponent(params.get('data')));
        const form = document.getElementById(formId);

        Object.keys(data).forEach(key => {
            if (form[key]) {
                form[key].value = data[key];
            }
        });
    }
}

// Fungsi untuk mengedit data barang
function editItem(index, dataKey) {
    const data = getFromLocalStorage(dataKey);
    const item = data[index];
    const encodedData = encodeURIComponent(JSON.stringify(item));

    window.location.href = `formbarang.html?data=${encodedData}`; // Kirim data ke form melalui URL
}

// Fungsi untuk menghapus data barang
function deleteItem(index, dataKey, tableId) {
    let data = getFromLocalStorage(dataKey);
    data.splice(index, 1); // Hapus data berdasarkan index
    saveToLocalStorage(dataKey, data);

    loadTableData(tableId, dataKey); // Reload tabel setelah penghapusan
    alert('Data berhasil dihapus!');
}

// Inisialisasi halaman daftarbarang.html
function initDaftarBarang() {
    const dataKey = 'barangData';
    const tableId = 'table-barang';

    loadTableData(tableId, dataKey);

    document.getElementById('searchInput').addEventListener('keyup', () => {
        searchTable('searchInput', tableId);
    });
}

// Inisialisasi halaman formbarang.html
function initFormBarang() {
    const formId = 'form-barang';
    const dataKey = 'barangData';

    loadFormDataFromURL(formId);

    document.getElementById('submit-button').addEventListener('click', (e) => {
        e.preventDefault();
        saveFormData(formId, dataKey);
    });
}

// Inisialisasi utama
document.addEventListener('DOMContentLoaded', () => {
    if (document.getElementById('table-barang')) {
        initDaftarBarang();
    } else if (document.getElementById('form-barang')) {
        initFormBarang();
    }
});