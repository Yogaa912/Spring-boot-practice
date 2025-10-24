// 这个函数是可选的，但很有用。因为你的后端返回的是 PageInfo 对象，
// 而 Bootstrap Table 默认需要一个纯数组。这个函数就是用来转换的。
function responseHandler(res) {
  // res 就是后端返回的整个 PageInfo JSON 对象
  return res.list; // 我们只返回核心的 list 数组
}

// “操作”列显示什么内容（比如按钮）
function permissionOperateFormatter(value, row, index) {
	  // 1. 从 body 标签读取当前登录用户的角色
	  const currentUserRole = $('body').data('currentUserRole'); // 建议修改 ('current-user-role')
 	  // 增加修改
      // 使用模板字面量 (`) 来构建 HTML 会更清晰
      let editButtonHtml = `
          <a href="#" class="btn btn-warning btn-sm edit-btn" 
             data-bs-toggle="modal" 
             data-bs-target="#permissionChangeModal"
             data-permission-id="${row.id}" 
             data-permission-name="${row.name}" 
             data-permission-uri="${row.uni}"
             data-permission-c="${row.c}" 
             data-permission-r="${row.r}" 
             data-permission-u="${row.u}" 
             data-permission-d="${row.d}"> 
              编辑
          </a>`;

      let deleteButtonHtml = '';
      if (currentUserRole === 'admin') {
          deleteButtonHtml = `
            <a class="btn btn-danger btn-sm delete-btn" href="#" title="删除" onclick="deleteById(${row.id})">
                删除
            </a>
          `;
      }

	  return `${editButtonHtml} ${deleteButtonHtml}`;
	  /** 
	  // 2. 先准备好通用的按钮（比如所有人都看得到的“修改密码”按钮）
	  let buttons = [
	    '<a class="btn btn-primary btn-sm password-change-btn" href="#" title="修改权限"',
	    ' data-bs-toggle="modal" data-bs-target="#permissionChangeModal" data-permission-id="' + row.id + '">',
	    '修改权限',
	    '</a>'
	  ];

	  // 3. 如果当前用户是 admin，才添加“删除”按钮
	  if (currentUserRole === 'admin') {
	    buttons.push(' '); // 加个空格
	    buttons.push(
	      '<a class="btn btn-danger btn-sm" href="#" title="删除权限" onclick="deleteById(' + row.id + ')">',
	      '删除权限',
	      '</a>'
	    );
	  }

	  // 4. 将按钮数组合并成最终的 HTML 字符串并返回
	  return buttons.join('');
	  */
}

// 如果你的删除按钮需要绑定更复杂的 jQuery 事件，可以使用这个
window.operateEvents = {
  // 例如: 'click .delete': function (e, value, row, index) { ... }
};
