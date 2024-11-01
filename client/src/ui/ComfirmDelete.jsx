import PropTypes from "prop-types";
import Button from "./Button";

function ConfirmDelete({ resource, onConfirm, onCloseModal, disabled }) {
  function handleConfirmClick() {
    onConfirm?.();
    onCloseModal?.();
  }

  return (
    <div className="flex w-full flex-col gap-4">
      <h3 className="text-lg font-semibold text-gray-800 md:text-xl">
        Delete {resource}
      </h3>
      <p className="mb-4 text-gray-500">
        Are you sure you want to delete{" "}
        <span className="font-bold text-gray-700">{resource}</span> permanently?
        This action cannot be undone.
      </p>

      <div className="flex justify-start gap-4">
        <Button
          textColor="text-violet-800"
          rounded="rounded-xl"
          className="w-20 border-2 border-violet-600 hover:bg-violet-100"
          onClick={onCloseModal}
        >
          Cancel
        </Button>
        <Button
          bgColor="bg-red-500"
          textColor="text-white"
          rounded="rounded-xl"
          className="w-22 hover:bg-red-700"
          onClick={handleConfirmClick}
          disabled={disabled}
        >
          Delete
        </Button>
      </div>
    </div>
  );
}

ConfirmDelete.propTypes = {
  resource: PropTypes.string.isRequired,
  onConfirm: PropTypes.func,
  disabled: PropTypes.bool,
  onCloseModal: PropTypes.func,
};

export default ConfirmDelete;
