'use strict';

function getCurrentUser() {
    fetch("api/user")
        .then(res => res.json())
        .then(user => {
            const roles = user.role.map(role => role.roleName.replace('ROLE_', '')).join(', ')
            $('#emailCurrentUser').append(`<span>${user.email}</span>`)
            $('#roleCurrentUser').append(`<span>${roles}</span>`)
        })
}

function getUserById(id) {
    fetch(`api/admin/${id}`)
        .then(res => res.json())
        .then(user => {
            const roles = user.role.map(role => role.roleName.replace('ROLE_', '')).join(', ')
            const u = `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.userName}</td>
                    <td>${user.name}</td>
                    <td>${user.surname}</td>
                    <td>${user.email}</td>
                    <td>${user.yearOfBirth}</td>
                    <td>${roles}</td>
                </tr>
            `;
            $('#oneUser').append(u)
        })
}

getCurrentUser()
getUserById(110)