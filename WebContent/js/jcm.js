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
	  modal.find('.modal-body p').text(
			  'data-title : ' + title + '<br>' +
			  'data-body : ' + body + '<br>' +
			  'data-location : ' + location + '<br>' +
			  'data-actionLabel : ' + action + '<br>'
			  ) 
	  document.getElementById('modalAction').innerHTML = action
	  document.getElementById('modalAction').onclick = function() {
		  window.location = newActionLocation
	  }
	  console.log('url sur laquelle pointe le bouton : ' + document.getElementById('modalAction').onclick )
	})
