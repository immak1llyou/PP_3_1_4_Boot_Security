let formEdit = document.forms["formEdit"];
editUser();

async function editModal(id) {
    const modal = new bootstrap.Modal(document.querySelector('#edit'));
    await openAndFillInTheModal(formEdit, modal, id);
}

function editUser() {
    formEdit.addEventListener("submit", ev => {
        ev.preventDefault();
        let roles = [];
        for (let i = 0; i < formEdit.roles.options.length; i++) {
            if (formEdit.roles.options[i].selected) {
                roles.push('ROLE_' + formEdit.roles.options[i].text);
            }
        }
        fetch("api/admin/" + formEdit.id.value, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: formEdit.id.value,
                userName: formEdit.username.value,
                name: formEdit.name.value,
                surname: formEdit.surname.value,
                email: formEdit.email.value,
                yearOfBirth: formEdit.yearOfBirth.value,
                password: formEdit.password.value,
                role: roles
            })
        }).then(() => {
            $('#closeEdit').click();
            getTableUser()
        });
    });
}