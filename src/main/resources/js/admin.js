document.addEventListener('DOMContentLoaded', async function () {
    await currentAdminEmailNavbar();
    await fillTableAboutUser();
    await fillTableAllUsers();
});

async function dataAboutAdmin() {
    const response = await fetch("/api/admin", {
        method: 'GET',
    })
    return await response.json();
}

async function dataAboutCurrentUser() {
    const responseGetCurrentUser = await fetch("/api/admin/2", {
        method: 'GET',
    })
    return await responseGetCurrentUser.json();
}

// async function dataAboutAllUsers() {
//     const responseGetAllUsers = await fetch("/api/admin", {
//         method: 'GET',
//     })
//     return await responseGetAllUsers.json();
// }

async function currentAdminEmailNavbar() {
    const currentAdminEmailNavbar = document.getElementById("currentAdminEmailNavbar")
    const admin = await dataAboutAdmin();
    currentAdminEmailNavbar.innerHTML =
        `<strong>${admin.email}</strong>
                 with roles:
                 ${admin.role.map(role => role.roleName).join(', ')}`;
}

async function fillTableAboutUser() {
    const currentUserTable = document.getElementById('tableOfCurrentUser');
    const currentUser = await dataAboutCurrentUser();
    let currentUserTableHTML = "";
    currentUserTableHTML +=
        `<tr>
            <td>${currentUser.id}</td>
             <td>${currentUser.userName}</td>
            <td>${currentUser.name}</td>
            <td>${currentUser.surname}</td>
            <td>${currentUser.email}</td>
            <td>${currentUser.yearOfBirth}</td>
            <td>${currentUser.role.map(role => role.roleName).join(' ')}</td>
        </tr>`
    currentUserTable.innerHTML = currentUserTableHTML;
}

//Таблица всех пользователей
async function fillTableAllUsers() {
    const allUsers = await fetch("/api/admin/users", {
        method: 'GET',
    }).then(response => response.json()).then(data => {
        const AllUsersTable = document.getElementById('allUsersTable');
        data.forEach(user => {
            const row = document.createElement('tr');
            row.innerHTML = `
         <td>${user.id}</td>
                    <td>${user.userName}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${user.yearOfBirth}</td>
                    <td>${user.role.map(role => role.roleName)}</td>
                    <td>
                        <button
                            type="button"
                            class="btn btn-info edit-btn"
                            data-toggle="modal"
                            data-target="#editUser"
                            data-userId="${user.id}"
                            >Edit
                        </button>
                    </td>
                    <td>
                        <button
                            type="button"
                            class="btn btn-danger delete-btn"
                            data-toggle="modal"
                            data-target="#userDeleteModal"
                            data-userId="${user.id}"
                        >Delete</button>
                    </td>
                `
            AllUsersTable.appendChild(row)
        })
    })

//Таблица создания пользователя
    const createUser = document.querySelector('.card-body-create-user')
    createUser.innerHTML = `
          <form>
                    <div class="text-center" style="text-align: center">
                        <label for="editId" class="col-form-label">ID</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="editId">
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="editUsername" class="col-form-label">Username</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="editUsername"></input>
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="editName" class="col-form-label">Name</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="editName"></input>
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="editSurname" class="col-form-label">Surname</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="editSurname"></input>
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="editEmail" class="col-form-label">Email</label>
                        <input type="email" class="form-control mx-auto" style="width: 30%" id="editEmail"></input>
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="passwordEdit" class="col-form-label">Password</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="passwordEdit"></input>
                    </div>
                    <div class="text-center" style="text-align: center">
                        <label for="yearOfBirthEdit" class="col-form-label">Year Of Birth</label>
                        <input type="number" class="form-control mx-auto" style="width: 30%" id="yearOfBirthEdit"></input>
                    </div>
                    <div class="text-center p-2" style="text-align: center">
                        <label for="rolesEdit" class="col-form-label">Roles</label>
                        <input type="text" class="form-control mx-auto" style="width: 30%" id="rolesEdit"></input>
                    </div>
            </div>
            <div class="d-grid gap-2 col-6 mx-auto p-3" style="width: 25%" >
                <button type="submit" class="btn btn-success">Save User</button>
            </div>
            </form>
                `


//Модальное окно редактирования
    const editButtons = document.querySelectorAll('.edit-btn');
    editButtons.forEach(editButton => {
        editButton.addEventListener('click', () => {
            const myModal = new bootstrap.Modal(document.getElementById('editUser'));
            myModal.show();
        });
    });
//Модальное окно удаления
    const deleteButtons = document.querySelectorAll('.delete-btn');
    deleteButtons.forEach(deleteButton => {
        deleteButton.addEventListener('click', () => {
            const myModal = new bootstrap.Modal(document.getElementById('deleteUser'));
            myModal.show();
        });
    });

}