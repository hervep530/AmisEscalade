/**
 * 
 */

console.log("test herve");


$('#confirmModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget) // Button that triggered the modal
	  var title = button.data('title')
	  var action = button.data('action')
	  var newActionLocation = button.data('location') // Extract info from data-* attributes
	  var body = button.data('body')
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  var actionLocation = document.getElementById('modalAction').onclick
	  console.log('url de l action : ' + newActionLocation)
	  console.log('url sur laquelle pointe le bouton : ' + actionLocation )
	  modal.find('.modal-title').text(title)
	  modal.find('.modal-body p').text(body) 
	  document.getElementById('modalAction').innerHTML = action
	  document.getElementById('modalAction').onclick = function() {
		  window.location = newActionLocation
	  }
	  console.log('url sur laquelle pointe le bouton : ' + document.getElementById('modalAction').onclick )
	})

function readContent(contentId){
	// set active message (in order to display all content)
	setActiveContent(contentId);
	// set active link on displayed message (background is modified, for example)
	setActiveLink(contentId);
}

	
function setActiveContent(contentId){
	// masking all contents
	var c = 0;
	var content = document.getElementById("content-" + c );
	while ( content != null ) {
		if ( ! content.className.includes(' masked-message')) { 
			content.className = content.className + " masked-message";
		}
		c++;
		content = document.getElementById("content-" + c);
	}
	// and unmasking readable content 
	console.log("content-" + contentId)
	content = document.getElementById("content-" + contentId);
	var contentClasses = content.className;
	if ( contentClasses.includes(' masked-message')) { 
		content.className = contentClasses.replace( / masked-message/g , ''); 
	}
}

function setActiveLink(contentId){
	// unlighting old active link
	var c = 0;
	var link = document.getElementById("message-link-" + c );
	while ( link != null ) {
		if ( link.className.includes(' active-message-link') ) { 
			link.className = link.className.replace( / active-message-link/g , '');
		}
		c++;
		link = document.getElementById("message-link-" + c);
	}
	// lighting active link
	link = document.getElementById("message-link-" + contentId );
	console.log("message-link-" + contentId);
	var linkClasses = " active-message-link";
	if ( link != null) linkClasses = link.className;
	if ( ! linkClasses.includes(" active-message-link") ) link.className = linkClasses + " active-message-link";
}

	
	