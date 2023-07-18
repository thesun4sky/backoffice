const host = 'http://' + window.location.host;

function toggleAdminSection() {
    var adminSection = document.getElementById("admin-section");
    var adminCheckbox = document.getElementById("admin-check");

    if (adminCheckbox.checked) {
        adminSection.style.display = "block";
    } else {
        adminSection.style.display = "none";
    }
}

function signup() {
    var username = document.getElementById('signupusername').value;
    var password = document.getElementById('signuppassword').value;
    var nickname = document.getElementById('signupnickname').value;
    var adminToken = document.getElementById('admin-token').value;
    var isAdmin = document.getElementById('admin-check').checked;

    console.log(username, password, nickname, adminToken, isAdmin);

    if (username === '' || password === '' || nickname === '') {
        alert('회원가입 정보를 모두 입력해주세요.');
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/api/auth/signup',
        data: JSON.stringify({
            username: username,
            password: password,
            nickname: nickname,
            adminToken: adminToken,
            admin: isAdmin
        }),
        contentType: 'application/json',
        success: function (response) {
            alert('회원가입이 성공적으로 완료되었습니다.');
            window.location.href = host + '/api/auth/login-page';
        },
        error: function (jqXHR, textStatus) {
            alert('회원가입에 실패하였습니다. 다시 시도해주세요.');
        }

    });

}
function login() {
    var username = document.getElementById('loginusername').value;
    var password = document.getElementById('loginpassword').value;

    console.log(username, password);

    if (username === '' || password === '') {
        alert('정보를 모두 입력해주세요.');
        return;
    }

    $.ajax({
        type: "POST",
        url: `/api/auth/login`,
        contentType: "application/json",
        data: JSON.stringify({username: username, password: password}),
    })
        .done(function (res, status, xhr) {
            const token = xhr.getResponseHeader('Authorization');

            Cookies.set('Authorization', token, {path: '/'})

            $.ajaxPrefilter(function (options, originalOptions, jqXHR) {
                jqXHR.setRequestHeader('Authorization', token);
            });

            window.location.href = host;
        })
        .fail(function (jqXHR, textStatus) {
            alert("로그인 실패")
        });
}

